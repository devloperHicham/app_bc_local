package com.schedulerates.setting.repository;

import com.schedulerates.setting.model.faq.entity.FaqEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface named {@link FaqRepository} for managing FaqEntity
 * objects in the database.
 */
public interface FaqRepository extends JpaRepository<FaqEntity, String> {

        /**
         * Checks if a FAQ entity with the given full name exists in the database.
         *
         * @param fullName the full name of the FAQ to check for existence
         * @return true if a FAQ entity with the given full name exists, false otherwise
         */
        boolean existsFaqEntityByFullName(final String fullName);

        /************* ✨ Windsurf Command ⭐ *************/
        /**
         * Finds a page of FAQs that are active (i.e., their 'active' field is '1') and
         * were created by the given user.
         *
         * @param createdBy the user who created the FAQs to search for
         * @param pageable  the pagination information
         * @return a page of FAQ entities that match the search criteria
         */
        /******* 7329a9ab-30dd-4689-8235-6c526a721de3 *******/
        @Query("""
                        SELECT s FROM FaqEntity s
                        WHERE s.active = '1'
                        AND s.createdBy = :createdBy
                        """)
        Page<FaqEntity> findByFiltersAndCreatedBy(
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        /**
         * Finds a page of FAQs that are active (i.e., their 'active' field is '1') and
         * were NOT created by the given user.
         *
         * @param createdBy the user who did NOT create the FAQs to search for
         * @param pageable  the pagination information
         * @return a page of FAQ entities that match the search criteria
         */
        @Query("""
                        SELECT s FROM FaqEntity s
                        WHERE s.active = '1'
                        AND s.createdBy != :createdBy
                        """)
        Page<FaqEntity> findAllOperatorAndActive(
                        @Param("createdBy") String createdBy,
                        Pageable pageable);

        /**
         * Finds a page of FAQs by their full name, ignoring case sensitivity, that are
         * active (i.e., their 'active' field is '1').
         *
         * @param fullName the full name of the FAQs to search for
         * @param pageable the pagination information
         * @return a page of FAQ entities that match the search criteria
         */

        @Query("""
                        SELECT s FROM FaqEntity s
                        WHERE s.active = '1'
                        AND s.fullName = :fullName
                        """)
        Page<FaqEntity> findByFiltersAndOperators(
                        @Param("fullName") String fullName,
                        Pageable pageable);

        /**
         * Finds a list of active FAQ entities.
         *
         * @return a list of active FAQ entities
         */
        @Query("SELECT f FROM FaqEntity f WHERE f.active = '1'")
        List<FaqEntity> findAllActive();
}