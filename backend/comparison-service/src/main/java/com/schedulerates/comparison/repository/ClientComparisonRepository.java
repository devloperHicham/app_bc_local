package com.schedulerates.comparison.repository;

import com.schedulerates.comparison.model.comparison.entity.ClientComparisonEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientComparisonRepository extends JpaRepository<ClientComparisonEntity, String> {
  Optional<ClientComparisonEntity> findByCompanyId(String companyId);
}