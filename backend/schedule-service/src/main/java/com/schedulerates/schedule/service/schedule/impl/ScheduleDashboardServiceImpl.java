package com.schedulerates.schedule.service.schedule.impl;

import com.schedulerates.schedule.model.auth.enums.TokenClaims;
import com.schedulerates.schedule.model.schedule.dto.response.DailyScheduleByUsersData;
import com.schedulerates.schedule.model.schedule.dto.response.DashboardResponse;
import com.schedulerates.schedule.model.schedule.dto.response.WeeklyScheduleByCompaniesData;
import com.schedulerates.schedule.model.schedule.dto.response.WeeklyScheduleData;
import com.schedulerates.schedule.model.schedule.dto.response.WeeklyScheduleData;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleDashboardService;
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
public class ScheduleDashboardServiceImpl implements ScheduleDashboardService {

    private final ScheduleRepository scheduleRepository;
    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public DashboardResponse getScheduleStats() {
        String userEmail = getCurrentUserEmail();
        boolean isAdmin = isAdmin();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        DashboardResponse response = new DashboardResponse();

        if (isAdmin) {
            response.setTotalAdminSchedules(scheduleRepository.countTodaySchedulesForAdmin().intValue());
            response.setTotalAdminScoreSchedules(calculateAdminScore(yesterday));
        } else {
            response.setTotalUserScheduleTodays(scheduleRepository.countTodaySchedulesByUser(userEmail).intValue());
            response.setTotalUserScheduleYesterdays(
                    scheduleRepository.countYesterdaySchedulesByUser(userEmail, yesterday).intValue());
            response.setScoreUserSchedules(calculateUserScore(userEmail));
        }

        return response;
    }
    
    @Override
    public List<DailyScheduleByUsersData> getDailyScheduleByUsers() {
        LocalDate today = LocalDate.now();
        List<Object[]> results = scheduleRepository.findDailyUsersScheduleCounts(today);

        return results.stream()
            .map(row -> new DailyScheduleByUsersData((String) row[0], ((Long) row[1]).intValue()))
            .toList();
    }

    @Override
    public List<WeeklyScheduleData> getGraphicSchedules() {
        String userEmail = getCurrentUserEmail();
        boolean isAdmin = isAdmin();

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        List<WeeklyScheduleData> weeklyData = new ArrayList<>();
        WeeklyScheduleData currentWeek = new WeeklyScheduleData();
        currentWeek.setWeek("Current Week");
        currentWeek.setSchedules(new ArrayList<>());

        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            int count = isAdmin
                    ? scheduleRepository.countByDate(day).intValue()
                    : scheduleRepository.countByDateAndUser(day, userEmail).intValue();
            currentWeek.getSchedules().add(count);
        }

        weeklyData.add(currentWeek);
        return weeklyData;
    }

    @Override
    public List<WeeklyScheduleByCompaniesData> getGraphicScheduleByCompanies() {
        if (!isAdmin()) {
            throw new SecurityException("Access denied: Admins only");
        }

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        List<Object[]> results = scheduleRepository.findWeeklyCompanyScheduleCounts(startOfWeek, endOfWeek);

        Map<String, WeeklyScheduleByCompaniesData> companyDataMap = new HashMap<>();

        for (Object[] row : results) {
            // Convert the java.sql.Date to LocalDate safely
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            String companyName = (String) row[1];
            Long count = (Long) row[2];

            int dayIndex = date.getDayOfWeek().getValue() - 1;

            WeeklyScheduleByCompaniesData data = companyDataMap.computeIfAbsent(companyName,
                    name -> WeeklyScheduleByCompaniesData.builder()
                            .companyName(name)
                            .schedules(new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0)))
                            .build());

            data.getSchedules().set(dayIndex, count.intValue());
        }

        return new ArrayList<>(companyDataMap.values());
    }

    private int calculateAdminScore(LocalDate yesterday) {
        Long todayCount = scheduleRepository.countTodaySchedulesForAdmin();
        Long yesterdayCount = scheduleRepository.countYesterdaySchedulesForAdmin(yesterday);

        if (yesterdayCount == 0) {
            return todayCount == 0 ? 0 : 100;
        }
        return (int) ((todayCount - yesterdayCount) * 100 / yesterdayCount);
    }

    private int calculateUserScore(String userEmail) {
        Long userTodayCount = scheduleRepository.countTodaySchedulesByUser(userEmail);
        Long totalTodayCount = scheduleRepository.countTodaySchedulesForAdmin();

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