package com.schedulerates.user.repository;

import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link UserEntity} instances.
 * Provides CRUD operations and additional query methods for {@link UserEntity}.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

  /**
   * Checks if a {@link UserEntity} with the given email exists.
   *
   * @param email the email to check.
   * @return {@code true} if a user with the specified email exists, otherwise
   *         {@code false}.
   */
  boolean existsUserEntityByEmail(final String email);

  /**
   * Finds a {@link UserEntity} by its email.
   *
   * @param email the email to search for.
   * @return an {@link Optional} containing the {@link UserEntity} if found, or
   *         {@link Optional#empty()} if not.
   */
  Optional<UserEntity> findUserEntityByEmail(final String email);

  // ✅ New method to get only ACTIVE users with pagination
  Page<UserEntity> findAllByUserStatus(UserStatus status, Pageable pageable);

  /**
   * find list of company entity in the database.
   *
   * @return a list of company entity
   */
  @NonNull
  List<UserEntity> findAll();

  Optional<UserEntity> findByEmail(String email);

  List<UserEntity> findByEmailIn(List<String> emails);
}
