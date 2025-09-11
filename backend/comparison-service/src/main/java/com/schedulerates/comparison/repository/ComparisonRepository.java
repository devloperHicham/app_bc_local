package com.schedulerates.comparison.repository;

import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
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

public interface ComparisonRepository extends JpaRepository<ComparisonEntity, String> {

        boolean existsByPortFromIdAndPortToIdAndCompanyIdAndContainerIdAndDateDepartAndDateArriveAndActive(
                        String portFromId,
                        String portToId,
                        String companyId,
                        String containerId,
                        String dateDepart,
                        String dateArrive,
                        String active);

        // === TEXT SEARCH ===

        // ADMIN: text search, created today (PostgreSQL-optimized)
        @Query("""
                        SELECT s FROM ComparisonEntity s
                        WHERE s.active = '1'
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (
                            LOWER(s.portFromName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.portToName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.companyName) LIKE LOWER(CONCAT('%', :search, '%'))
                        )
                        """)
        Page<ComparisonEntity> findByTextSearch(@Param("search") String search, Pageable pageable);

        // USER: text search, created today (PostgreSQL-optimized)
        @Query("""
                        SELECT s FROM ComparisonEntity s
                        WHERE s.active = '1'
                        AND s.createdBy = :createdBy
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (
                            LOWER(s.portFromName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.portToName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(s.companyName) LIKE LOWER(CONCAT('%', :search, '%'))
                        )
                        """)
        Page<ComparisonEntity> findByTextSearchAndCreatedBy(
                        @Param("search") String search,
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        // === FILTER SEARCH ===

        // ADMIN: filters, created today (PostgreSQL-optimized)
        @Query("""
                        SELECT s FROM ComparisonEntity s
                        WHERE s.active = '1'
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (:portFromId IS NULL OR s.portFromId = :portFromId)
                        AND (:portToId IS NULL OR s.portToId = :portToId)
                        AND (:dateDepart IS NULL OR s.dateDepart = :dateDepart)
                        AND (:dateArrive IS NULL OR s.dateArrive = :dateArrive)
                        AND (:companyId IS NULL OR s.companyId = :companyId)
                        """)
        Page<ComparisonEntity> findByFilters(
                        @Param("portFromId") @Nullable String portFromId,
                        @Param("portToId") @Nullable String portToId,
                        @Param("dateDepart") @Nullable String dateDepart,
                        @Param("dateArrive") @Nullable String dateArrive,
                        @Param("companyId") @Nullable String companyId,
                        Pageable pageable);

        // USER: filters + createdBy, created today (PostgreSQL-optimized)
        @Query("""
                        SELECT s FROM ComparisonEntity s
                        WHERE s.active = '1'
                        AND s.createdBy = :createdBy
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        AND (:portFromId IS NULL OR s.portFromId = :portFromId)
                        AND (:portToId IS NULL OR s.portToId = :portToId)
                        AND (:dateDepart IS NULL OR s.dateDepart = :dateDepart)
                        AND (:dateArrive IS NULL OR s.dateArrive = :dateArrive)
                        AND (:companyId IS NULL OR s.companyId = :companyId)
                        """)
        Page<ComparisonEntity> findByFiltersAndCreatedBy(
                        @Param("portFromId") @Nullable String portFromId,
                        @Param("portToId") @Nullable String portToId,
                        @Param("dateDepart") @Nullable String dateDepart,
                        @Param("dateArrive") @Nullable String dateArrive,
                        @Param("companyId") @Nullable String companyId,
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        // === GET ALL ACTIVE CREATED TODAY ===

        // ADMIN: active comparisons created today (PostgreSQL-optimized)
        @Query("""
                        SELECT s FROM ComparisonEntity s
                        WHERE s.active = :active
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        """)
        Page<ComparisonEntity> findTodayActive(@Param("active") String active, Pageable pageable);

        // USER: active comparisons created today by specific user
        // (PostgreSQL-optimized)
        @Query("""
                        SELECT s FROM ComparisonEntity s
                        WHERE s.active = :active
                        AND s.createdBy = :createdBy
                        AND CAST(s.createdAt AS localdate) = CURRENT_DATE
                        """)
        Page<ComparisonEntity> findByActiveAndCreatedByToday(
                        @Param("active") String active,
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        // === FIND BY ID + ACTIVE ===
        Optional<ComparisonEntity> findByIdAndActive(String id, String active);

        // === THIS FOR FORGIN CLIENT BY ID + ACTIVE ===
        boolean existsByCompanyIdAndActive(String companyId, String active);

        boolean existsByPortFromIdAndActive(String portFromId, String active);

        boolean existsByPortToIdAndActive(String portToId, String active);

        boolean existsByContainerIdAndActive(String containerId, String active);

        boolean existsByGargoIdAndActive(String gargoId, String active);

        boolean existsByTransportationIdAndActive(String transportationId, String active);

        /**
         * ADMIN: filters, created between dateDebut and dateFin
         * (PostgreSQL-optimized)
         * <p>
         * Note: the dateDebut and dateFin are optional, and if they are null, the query
         * returns all Comparisons created before the current date and time.
         * 
         * @param portFromId the departure port
         * @param portToId   the arrival port
         * @param dateDebut  the start date
         * @param dateFin    the end date
         * @param userId     the user id
         * @param pageable   the paging information
         * @return a page of ComparisonEntity
         */
        @Query("""
                        SELECT s FROM ComparisonEntity s
                        WHERE s.active = '1'
                        AND (:portFromId IS NULL OR s.portFromId = :portFromId)
                        AND (:portToId IS NULL OR s.portToId = :portToId)
                        AND (COALESCE(:dateDebut, s.createdAt) <= s.createdAt)
                        AND (COALESCE(:dateFin, s.createdAt) >= s.createdAt)
                        AND (:userId IS NULL OR s.createdBy = :userId)
                        """) 
        Page<ComparisonEntity> findByFilterHistoriques(
                        @Param("portFromId") @Nullable String portFromId,
                        @Param("portToId") @Nullable String portToId,
                        @Param("dateDebut") @Nullable LocalDateTime dateDebut,
                        @Param("dateFin") @Nullable LocalDateTime dateFin,
                        @Param("userId") @Nullable String userId,
                        Pageable pageable);

        // ADMIN: active schedules created yesterday
        @Query("SELECT s FROM ComparisonEntity s " +
                        "WHERE s.active = :active " +
                        "AND s.createdAt >= :startOfDay " +
                        "AND s.createdAt < :startOfNextDay")
        Page<ComparisonEntity> findYesterdayActive(
                        @Param("active") String active,
                        @Param("startOfDay") LocalDateTime startOfDay,
                        @Param("startOfNextDay") LocalDateTime startOfNextDay,
                        Pageable pageable);

        // ================ DASHBOARD STATISTICS METHODS ================ //

        // Count today's schedules for a specific user
        @Query("SELECT COUNT(s) FROM ComparisonEntity s WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) = CURRENT_DATE")
        Long countTodayComparisonsByUser(@Param("email") String userEmail);

        // Count yesterday's Comparisons for a specific user
        @Query("SELECT COUNT(s) FROM ComparisonEntity s WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) = :yesterday")
        Long countYesterdayComparisonsByUser(@Param("email") String userEmail,
                        @Param("yesterday") LocalDate yesterday);

        // Count today's Comparisons for all users (admin view)
        @Query("SELECT COUNT(s) FROM ComparisonEntity s WHERE CAST(s.createdAt AS DATE) = CURRENT_DATE")
        Long countTodayComparisonsForAdmin();

        // Count yesterday's Comparisons for all users (admin view)
        @Query("SELECT COUNT(s) FROM ComparisonEntity s WHERE CAST(s.createdAt AS DATE) = :yesterday")
        Long countYesterdayComparisonsForAdmin(@Param("yesterday") LocalDate yesterday);

        // Count Comparisons for a specific date (admin view)
        @Query("SELECT COUNT(s) FROM ComparisonEntity s WHERE CAST(s.createdAt AS DATE) = :date")
        Long countByDate(@Param("date") LocalDate date);

        // Count Comparisons for a specific date and user
        @Query("SELECT COUNT(s) FROM ComparisonEntity s WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) = :date")
        Long countByDateAndUser(@Param("date") LocalDate date, @Param("email") String userEmail);

        // Get weekly Comparison counts for admin
        @Query("SELECT CAST(s.createdAt AS DATE) as day, COUNT(s) as count FROM ComparisonEntity s " +
                        "WHERE CAST(s.createdAt AS DATE) BETWEEN :startDate AND :endDate " +
                        "GROUP BY CAST(s.createdAt AS DATE) " +
                        "ORDER BY day")
        List<Object[]> findWeeklyComparisonCounts(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        // Get weekly Comparison counts for a specific user
        @Query("SELECT CAST(s.createdAt AS DATE) as day, COUNT(s) as count FROM ComparisonEntity s " +
                        "WHERE s.createdBy = :email AND CAST(s.createdAt AS DATE) BETWEEN :startDate AND :endDate " +
                        "GROUP BY CAST(s.createdAt AS DATE) " +
                        "ORDER BY day")
        List<Object[]> findWeeklyComparisonCountsByUser(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("email") String userEmail);

    // Get weekly Comparison counts by company
       @Query("""
            SELECT CAST(s.createdAt AS DATE) AS day, s.companyName, COUNT(s) 
            FROM ComparisonEntity s
            WHERE CAST(s.createdAt AS DATE) BETWEEN :startDate AND :endDate
            GROUP BY CAST(s.createdAt AS DATE), s.companyName
            ORDER BY day, s.companyName
        """)
        List<Object[]> findWeeklyCompanyComparisonCounts(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // Get daily Comparison counts by user
        @Query("""
            SELECT s.createdBy, COUNT(s)
            FROM ComparisonEntity s
            WHERE CAST(s.createdAt AS DATE) = :today
            AND s.active = '1'
            GROUP BY s.createdBy
            ORDER BY s.createdBy
        """)
        List<Object[]> findDailyUsersComparisonCounts(@Param("today") LocalDate today);

        /************************************************************************************************************ */
        /********************************** this party for client to gat data by search ***************************** */
        /************************************************************************************************************ */

    @Query(value = """
    SELECT c.* FROM comparison_entity c
    WHERE c.active = '1'
      AND (:portFromId IS NULL OR c.port_from_id = :portFromId)
      AND (:portToId IS NULL OR c.port_to_id = :portToId)
      AND ((:dateStart IS NULL OR :dateEnd IS NULL)
           OR (:searchOn = '1' AND c.date_depart BETWEEN :dateStart AND :dateEnd)
           OR (:searchOn = '2' AND c.date_arrive BETWEEN :dateStart AND :dateEnd))
      AND (:companyId IS NULL OR c.company_id = :companyId)
      AND (:isCheapest IS NULL OR (:isCheapest = true AND c.price = (
           SELECT MIN(c2.price) FROM comparison_entity c2
           WHERE c2.port_from_id = c.port_from_id
             AND c2.port_to_id = c.port_to_id
             AND c2.active = '1'
       )))
      AND (:isFastest IS NULL OR (:isFastest = true AND 
           EXTRACT(EPOCH FROM (c.date_arrive - c.date_depart)) = (
           SELECT MIN(EXTRACT(EPOCH FROM (c2.date_arrive - c2.date_depart))) 
           FROM comparison_entity c2
           WHERE c2.port_from_id = c.port_from_id
             AND c2.port_to_id = c.port_to_id
             AND c2.active = '1'
       )))
      AND (:isDirect IS NULL OR (:isDirect = true AND c.transportation_name NOT LIKE '%Transshipment%'))
""", 
countQuery = """
    SELECT COUNT(*) FROM comparison_entity c
    WHERE c.active = '1'
      AND (:portFromId IS NULL OR c.port_from_id = :portFromId)
      AND (:portToId IS NULL OR c.port_to_id = :portToId)
      AND ((:dateStart IS NULL OR :dateEnd IS NULL)
           OR (:searchOn = '1' AND c.date_depart BETWEEN :dateStart AND :dateEnd)
           OR (:searchOn = '2' AND c.date_arrive BETWEEN :dateStart AND :dateEnd))
      AND (:companyId IS NULL OR c.company_id = :companyId)
      AND (:isCheapest IS NULL OR (:isCheapest = true AND c.price = (
           SELECT MIN(c2.price) FROM comparison_entity c2
           WHERE c2.port_from_id = c.port_from_id
             AND c2.port_to_id = c.port_to_id
             AND c2.active = '1'
       )))
      AND (:isFastest IS NULL OR (:isFastest = true AND 
           EXTRACT(EPOCH FROM (c.date_arrive - c.date_depart)) = (
           SELECT MIN(EXTRACT(EPOCH FROM (c2.date_arrive - c2.date_depart))) 
           FROM comparison_entity c2
           WHERE c2.port_from_id = c.port_from_id
             AND c2.port_to_id = c.port_to_id
             AND c2.active = '1'
       )))
      AND (:isDirect IS NULL OR (:isDirect = true AND c.transportation_name NOT LIKE '%Transshipment%'))
""",
nativeQuery = true)
Page<ComparisonEntity> findByComparisonFilters(
        @Param("portFromId") String portFromId,
        @Param("portToId") String portToId,
        @Param("dateStart") LocalDate dateStart,
        @Param("dateEnd") LocalDate dateEnd,
        @Param("searchOn") String searchOn,
        @Param("companyId") String companyId,
        @Param("isCheapest") Boolean isCheapest,
        @Param("isFastest") Boolean isFastest,
        @Param("isDirect") Boolean isDirect,
        Pageable pageable
);

    }