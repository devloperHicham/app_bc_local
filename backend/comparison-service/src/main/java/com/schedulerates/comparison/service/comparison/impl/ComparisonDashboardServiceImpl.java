package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.model.auth.enums.TokenClaims;
import com.schedulerates.comparison.model.comparison.dto.response.DashboardResponse;
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
import java.util.List;
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
            response.setTotalAdminComparisons(comparisonRepository.countTodaySchedulesForAdmin().intValue());
            response.setTotalAdminScoreComparisons(calculateAdminScore(yesterday));
        } else {
            response.setTotalUserComparisonTodays(comparisonRepository.countTodaySchedulesByUser(userEmail).intValue());
            response.setTotalUserComparisonYesterdays(
                    comparisonRepository.countYesterdaySchedulesByUser(userEmail, yesterday).intValue());
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

    private int calculateAdminScore(LocalDate yesterday) {
        Long todayCount = comparisonRepository.countTodaySchedulesForAdmin();
        Long yesterdayCount = comparisonRepository.countYesterdaySchedulesForAdmin(yesterday);

        if (yesterdayCount == 0) {
            return todayCount == 0 ? 0 : 100;
        }
        return (int) ((todayCount - yesterdayCount) * 100 / yesterdayCount);
    }

    private int calculateUserScore(String userEmail) {
        Long userTodayCount = comparisonRepository.countTodaySchedulesByUser(userEmail);
        Long totalTodayCount = comparisonRepository.countTodaySchedulesForAdmin();

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
