package com.schedulerates.setting.repository;

import com.schedulerates.setting.model.company.entity.CompanyEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

/**
 * Repository interface named {@link CompanyRepository} for managing
 * CompanyEntity objects in the database.
 */
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {

  /**
   * Checks if a company entity with the given name exists in the database.
   *
   * @param companyName the name of the company to check for existence
   * @return true if a company entity with the given name exists, false otherwise
   */
  boolean existsCompanyEntityByCompanyName(final String companyName);

  /**
   * find list of company entity in the database.
   *
   * @return a list of company entity
   */
  @NonNull
  List<CompanyEntity> findAll();

  /**
   * Finds a page of FAQ entities by their full name, ignoring case sensitivity.
   *
   * @param companyName the full name to search for
   * @param pageable    the pagination information
   * @return a page of FAQ entities that match the search criteria
   */
  Page<CompanyEntity> findByCompanyNameContainingIgnoreCase(String companyName, Pageable pageable);
}