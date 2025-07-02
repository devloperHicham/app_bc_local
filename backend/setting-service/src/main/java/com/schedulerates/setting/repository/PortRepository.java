package com.schedulerates.setting.repository;

import com.schedulerates.setting.model.port.entity.PortEntity;
import org.springframework.lang.NonNull;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface named {@link PortRepository} for managing PortEntity objects in the database.
 */
public interface PortRepository extends JpaRepository<PortEntity, String> {

    /**
     * Checks if a Port entity with the given full name exists in the database.
     *
     * @param portName the full name of the Port to check for existence
     * @return true if a Port entity with the given full name exists, false otherwise
     */
    boolean existsPortEntityByPortName(final String portName);
   
      /**
     * find list of port entity in the database.
     *
     * @return a list of port entity
     */
    @NonNull
    List<PortEntity> findAll();
    
    /**
     * Finds a page of Port entities by their full name, ignoring case sensitivity.
     *
     * @param portName the full name to search for
     * @param pageable the pagination information
     * @return a page of Port entities that match the search criteria
     */
    Page<PortEntity> findByPortNameContainingIgnoreCase(String portName, Pageable pageable);

}