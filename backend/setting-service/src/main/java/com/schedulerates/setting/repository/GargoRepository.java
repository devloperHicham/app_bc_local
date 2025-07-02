package com.schedulerates.setting.repository;

import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import org.springframework.lang.NonNull;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface named {@link GargoRepository} for managing GargoEntity objects in the database.
 */
public interface GargoRepository extends JpaRepository<GargoEntity, String> {

    /**
     * Checks if a Gargo entity with the given full name exists in the database.
     *
     * @param gargoName the full name of the Gargo to check for existence
     * @return true if a Gargo entity with the given full name exists, false otherwise
     */
    boolean existsGargoEntityByGargoName(final String gargoName);
    
      /**
     * find list of gargo entity in the database.
     *
     * @return a list of gargo entity
     */
  @NonNull
    List<GargoEntity> findAll();
    
    /**
     * Finds a page of Gargo entities by their full name, ignoring case sensitivity.
     *
     * @param gargoName the full name to search for
     * @param pageable the pagination information
     * @return a page of Gargo entities that match the search criteria
     */
    Page<GargoEntity> findByGargoNameContainingIgnoreCase(String gargoName, Pageable pageable);

}