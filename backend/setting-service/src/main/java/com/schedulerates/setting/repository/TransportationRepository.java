package com.schedulerates.setting.repository;

import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import org.springframework.lang.NonNull;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface named {@link TransportationRepository} for managing TransportationEntity objects in the database.
 */
public interface TransportationRepository extends JpaRepository<TransportationEntity, String> {

    /**
     * Checks if a Transportation entity with the given full name exists in the database.
     *
     * @param transportationName the full name of the Transportation to check for existence
     * @return true if a Transportation entity with the given full name exists, false otherwise
     */
    boolean existsTransportationEntityByTransportationName(final String transportationName);
    
      /**
     * find list of transportation entity in the database.
     *
     * @return a list of transportation entity
     */
    @NonNull
    List<TransportationEntity> findAll();
    
    /**
     * Finds a page of Transportation entities by their full name, ignoring case sensitivity.
     *
     * @param transportationName the full name to search for
     * @param pageable the pagination information
     * @return a page of Transportation entities that match the search criteria
     */
    Page<TransportationEntity> findByTransportationNameContainingIgnoreCase(String transportationName, Pageable pageable);

}