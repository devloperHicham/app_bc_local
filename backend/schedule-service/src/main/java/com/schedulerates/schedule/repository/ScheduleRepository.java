package com.schedulerates.schedule.repository;

import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {

        boolean existsByPortFromIdAndPortToIdAndRefVoyageAndDateDepartAndDateArriveAndActive(
                        String portFromId,
                        String portToId,
                        String refVoyage,
                        String dateDepart,
                        String dateArrive,
                        String active);
        // === TEXT SEARCH ===

        // ADMIN: text search, created today
        @Query("""
                        SELECT s FROM ScheduleEntity s
                        WHERE s.active = '1'
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (
                            LOWER(s.portFromName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.portToName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.refVoyage) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.companyName) LIKE LOWER(CONCAT('%', :search, '%'))
                        )
                        """)
        Page<ScheduleEntity> findByTextSearch(@Param("search") String search, Pageable pageable);

        // USER: text search, created today
        @Query("""
                        SELECT s FROM ScheduleEntity s
                        WHERE s.active = '1'
                        AND s.createdBy = :createdBy
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (
                            LOWER(s.portFromName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.portToName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.refVoyage) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.companyName) LIKE LOWER(CONCAT('%', :search, '%'))
                        )
                        """)
        Page<ScheduleEntity> findByTextSearchAndCreatedBy(
                        @Param("search") String search,
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        // === FILTER SEARCH ===

        // ADMIN: filters, created today
        @Query("""
                        SELECT s FROM ScheduleEntity s
                        WHERE s.active = '1'
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (:portFromId IS NULL OR s.portFromId = :portFromId)
                        AND (:portToId IS NULL OR s.portToId = :portToId)
                        AND (:dateDepart IS NULL OR s.dateDepart = :dateDepart)
                        AND (:dateArrive IS NULL OR s.dateArrive = :dateArrive)
                        AND (:companyId IS NULL OR s.companyId = :companyId)
                        """)
        Page<ScheduleEntity> findByFilters(
                        @Param("portFromId") @Nullable String portFromId,
                        @Param("portToId") @Nullable String portToId,
                        @Param("dateDepart") @Nullable String dateDepart,
                        @Param("dateArrive") @Nullable String dateArrive,
                        @Param("companyId") @Nullable String companyId,
                        Pageable pageable);

        // USER: filters + createdBy, created today
        @Query("""
                        SELECT s FROM ScheduleEntity s
                        WHERE s.active = '1'
                        AND s.createdBy = :createdBy
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (:portFromId IS NULL OR s.portFromId = :portFromId)
                        AND (:portToId IS NULL OR s.portToId = :portToId)
                        AND (:dateDepart IS NULL OR s.dateDepart = :dateDepart)
                        AND (:dateArrive IS NULL OR s.dateArrive = :dateArrive)
                        AND (:companyId IS NULL OR s.companyId = :companyId)
                        """)
        Page<ScheduleEntity> findByFiltersAndCreatedBy(
                        @Param("portFromId") @Nullable String portFromId,
                        @Param("portToId") @Nullable String portToId,
                        @Param("dateDepart") @Nullable String dateDepart,
                        @Param("dateArrive") @Nullable String dateArrive,
                        @Param("companyId") @Nullable String companyId,
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        // === GET ALL ACTIVE CREATED TODAY ===

        // ADMIN: active schedules created today
        @Query("""
                        SELECT s FROM ScheduleEntity s
                        WHERE s.active = :active
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        """)
        Page<ScheduleEntity> findTodayActive(@Param("active") String active, Pageable pageable);

        // USER: active schedules created today by specific user
        @Query("""
                        SELECT s FROM ScheduleEntity s
                        WHERE s.active = :active
                        AND s.createdBy = :createdBy
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        """)
        Page<ScheduleEntity> findByActiveAndCreatedByToday(
                        @Param("active") String active,
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        // === FIND BY ID + ACTIVE ===
        Optional<ScheduleEntity> findByIdAndActive(String id, String active);

        // === THIS FOR FORGIN CLIENT BY ID + ACTIVE ===
        boolean existsByCompanyIdAndActive(String companyId, String active);

        boolean existsByPortFromIdAndActive(String portFromId, String active);

        boolean existsByPortToIdAndActive(String portToId, String active);

        /**
         * ADMIN: filters, created between dateDebut and dateFin
         * (PostgreSQL-optimized)
         * <p>
         * Note: the dateDebut and dateFin are optional, and if they are null, the query
         * returns all schedules created before the current date and time.
         * 
         * @param portFromId the departure port
         * @param portToId   the arrival port
         * @param dateDebut  the start date
         * @param dateFin    the end date
         * @param userId     the user id
         * @param pageable   the paging information
         * @return a page of ScheduleEntity
         */
        @Query("""
                        SELECT s FROM ScheduleEntity s
                        WHERE s.active = '1'
                        AND (:portFromId IS NULL OR s.portFromId = :portFromId)
                        AND (:portToId IS NULL OR s.portToId = :portToId)
                        AND (COALESCE(:dateDebut, s.createdAt) <= s.createdAt)
                        AND (COALESCE(:dateFin, s.createdAt) >= s.createdAt)
                        AND (:userId IS NULL OR s.createdBy = :userId)
                        """)
        Page<ScheduleEntity> findByFilterHistoriques(
                        @Param("portFromId") @Nullable String portFromId,
                        @Param("portToId") @Nullable String portToId,
                        @Param("dateDebut") @Nullable LocalDateTime dateDebut,
                        @Param("dateFin") @Nullable LocalDateTime dateFin,
                        @Param("userId") @Nullable String userId,
                        Pageable pageable);

        // ADMIN: active schedules created yesterday
        @Query("SELECT s FROM ScheduleEntity s " +
                        "WHERE s.active = :active " +
                        "AND s.createdAt >= :startOfDay " +
                        "AND s.createdAt < :startOfNextDay")
        Page<ScheduleEntity> findYesterdayActive(
                        @Param("active") String active,
                        @Param("startOfDay") LocalDateTime startOfDay,
                        @Param("startOfNextDay") LocalDateTime startOfNextDay,
                        Pageable pageable);

        // ================ DASHBOARD STATISTICS METHODS ================ //

        // Count today's schedules for a specific user
        @Query("SELECT COUNT(s) FROM ScheduleEntity s WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) = CURRENT_DATE")
        Long countTodaySchedulesByUser(@Param("email") String userEmail);

        // Count yesterday's schedules for a specific user
        @Query("SELECT COUNT(s) FROM ScheduleEntity s WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) = :yesterday")
        Long countYesterdaySchedulesByUser(@Param("email") String userEmail,
                        @Param("yesterday") LocalDate yesterday);

        // Count today's schedules for all users (admin view)
        @Query("SELECT COUNT(s) FROM ScheduleEntity s WHERE CAST(s.createdAt AS DATE) = CURRENT_DATE")
        Long countTodaySchedulesForAdmin();

        // Count yesterday's schedules for all users (admin view)
        @Query("SELECT COUNT(s) FROM ScheduleEntity s WHERE CAST(s.createdAt AS DATE) = :yesterday")
        Long countYesterdaySchedulesForAdmin(@Param("yesterday") LocalDate yesterday);

        // Count schedules for a specific date (admin view)
        @Query("SELECT COUNT(s) FROM ScheduleEntity s WHERE CAST(s.createdAt AS DATE) = :date")
        Long countByDate(@Param("date") LocalDate date);

        // Count schedules for a specific date and user
        @Query("SELECT COUNT(s) FROM ScheduleEntity s WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) = :date")
        Long countByDateAndUser(@Param("date") LocalDate date, @Param("email") String userEmail);

        // Get weekly schedule counts for admin
        @Query("SELECT CAST(s.createdAt AS DATE) as day, COUNT(s) as count FROM ScheduleEntity s " +
                        "WHERE CAST(s.createdAt AS DATE) BETWEEN :startDate AND :endDate " +
                        "GROUP BY CAST(s.createdAt AS DATE) " +
                        "ORDER BY day")
        List<Object[]> findWeeklyScheduleCounts(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        // Get weekly schedule counts for a specific user
        @Query("SELECT CAST(s.createdAt AS DATE) as day, COUNT(s) as count FROM ScheduleEntity s " +
                        "WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) BETWEEN :startDate AND :endDate " +
                        "GROUP BY CAST(s.createdAt AS DATE) " +
                        "ORDER BY day")
        List<Object[]> findWeeklyScheduleCountsByUser(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("email") String userEmail);

        // Get weekly schedule counts by company
       @Query("""
            SELECT CAST(s.createdAt AS DATE) AS day, s.companyName, COUNT(s) 
            FROM ScheduleEntity s
            WHERE CAST(s.createdAt AS DATE) BETWEEN :startDate AND :endDate
            GROUP BY CAST(s.createdAt AS DATE), s.companyName
            ORDER BY day, s.companyName
        """)
        List<Object[]> findWeeklyCompanyScheduleCounts(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

            
        // count schedules created today by users
        @Query("""
            SELECT s.createdBy, COUNT(s)
            FROM ScheduleEntity s
            WHERE CAST(s.createdAt AS DATE) = :today
            AND s.active = '1'
            GROUP BY s.createdBy
            ORDER BY s.createdBy
        """)
        List<Object[]> findDailyUsersScheduleCounts(@Param("today") LocalDate today);
}
