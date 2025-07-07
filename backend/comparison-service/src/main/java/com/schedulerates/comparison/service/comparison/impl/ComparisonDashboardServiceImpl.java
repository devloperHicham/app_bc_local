package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.model.auth.enums.TokenClaims;
import com.schedulerates.comparison.model.comparison.dto.response.DashboardResponse;
import com.schedulerates.comparison.model.comparison.dto.response.WeeklyComparisonByCompaniesData;
import com.schedulerates.comparison.model.comparison.dto.response.WeeklyComparisonData;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.service.comparison.ComparisonDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComparisonDashboardServiceImpl implements ComparisonDashboardService {

    private final ComparisonRepository comparisonRepository;
    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public DashboardResponse getComparisonStats() {
        String userEmail = getCurrentUserEmail();
        boolean isAdmin = isAdmin();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        DashboardResponse response = new DashboardResponse();

        if (isAdmin) {
            response.setTotalAdminComparisons(comparisonRepository.countTodayComparisonsForAdmin().intValue());
            response.setTotalAdminScoreComparisons(calculateAdminScore(yesterday));
        } else {
            response.setTotalUserComparisonTodays(
                    comparisonRepository.countTodayComparisonsByUser(userEmail).intValue());
            response.setTotalUserComparisonYesterdays(
                    comparisonRepository.countYesterdayComparisonsByUser(userEmail, yesterday).intValue());
            response.setScoreUserComparisons(calculateUserScore(userEmail));
        }

        return response;
    }

    @Override
    public List<WeeklyComparisonData> getGraphicComparisons() {
        String userEmail = getCurrentUserEmail();
        boolean isAdmin = isAdmin();

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        List<WeeklyComparisonData> weeklyData = new ArrayList<>();
        WeeklyComparisonData currentWeek = new WeeklyComparisonData();
        currentWeek.setWeek("Current Week");
        currentWeek.setComparisons(new ArrayList<>());

        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            int count = isAdmin
                    ? comparisonRepository.countByDate(day).intValue()
                    : comparisonRepository.countByDateAndUser(day, userEmail).intValue();
            currentWeek.getComparisons().add(count);
        }

        weeklyData.add(currentWeek);
        return weeklyData;
    }

    @Override
    public List<WeeklyComparisonByCompaniesData> getGraphicComparisonByCompanies() {
        if (!isAdmin()) {
            throw new SecurityException("Access denied: Admins only");
        }

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        List<Object[]> results = comparisonRepository.findWeeklyCompanyComparisonCounts(startOfWeek, endOfWeek);

        Map<String, WeeklyComparisonByCompaniesData> companyDataMap = new HashMap<>();

        for (Object[] row : results) {
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate(); // Safe conversion
            String companyName = (String) row[1];
            Long count = (Long) row[2];

            int dayIndex = date.getDayOfWeek().getValue() - 1;

            WeeklyComparisonByCompaniesData data = companyDataMap.computeIfAbsent(companyName,
                    name -> WeeklyComparisonByCompaniesData.builder()
                            .companyName(name)
                            .comparisons(new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0)))
                            .build());

            data.getComparisons().set(dayIndex, count.intValue());
        }

        return new ArrayList<>(companyDataMap.values());
    }

    private int calculateAdminScore(LocalDate yesterday) {
        Long todayCount = comparisonRepository.countTodayComparisonsForAdmin();
        Long yesterdayCount = comparisonRepository.countYesterdayComparisonsForAdmin(yesterday);

        if (yesterdayCount == 0) {
            return todayCount == 0 ? 0 : 100;
        }
        return (int) ((todayCount - yesterdayCount) * 100 / yesterdayCount);
    }

    private int calculateUserScore(String userEmail) {
        Long userTodayCount = comparisonRepository.countTodayComparisonsByUser(userEmail);
        Long totalTodayCount = comparisonRepository.countTodayComparisonsForAdmin();

        if (totalTodayCount == 0) {
            return 0;
        }
        return (int) (userTodayCount * 100 / totalTodayCount);
    }

    private String getCurrentUserEmail() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !ANONYMOUS_USER.equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USER_EMAIL.getValue()).toString())
                .orElse(ANONYMOUS_USER);
    }

    private boolean isAdmin() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !ANONYMOUS_USER.equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USER_TYPE.getValue()).toString())
                .orElse(ANONYMOUS_USER)
                .equalsIgnoreCase("ADMIN");
    }
}
