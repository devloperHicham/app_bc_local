package com.schedulerates.setting.repository;

import com.schedulerates.setting.model.container.entity.ContainerEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

/**
 * Repository interface named {@link ContainerRepository} for managing
 * ContainerEntity objects in the database.
 */
public interface ContainerRepository extends JpaRepository<ContainerEntity, String> {

  /**
   * Checks if a Container entity with the given full name exists in the database.
   *
   * @param containerName the full name of the Container to check for existence
   * @return true if a Container entity with the given full name exists, false
   *         otherwise
   */
  boolean existsContainerEntityBycontainerName(final String containerName);

  /**
   * find list of container entity in the database.
   *
   * @return a list of container entity
   */
  @NonNull
  List<ContainerEntity> findAll();

  /**
   * Finds a page of Container entities by their full name, ignoring case
   * sensitivity.
   *
   * @param containerName the full name to search for
   * @param pageable      the pagination information
   * @return a page of Container entities that match the search criteria
   */
  Page<ContainerEntity> findBycontainerNameContainingIgnoreCase(String containerName, Pageable pageable);

}