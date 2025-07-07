package com.schedulerates.user.service.impl;

import com.schedulerates.user.exception.PasswordNotValidException;
import com.schedulerates.user.exception.UserNotFoundException;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.UserResetPasswordRequest;
import com.schedulerates.user.model.user.dto.request.UserUpdatePasswordRequest;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.mapper.UserEntityToUserMapper;
import com.schedulerates.user.repository.UserRepository;
import com.schedulerates.user.service.RegisterService;
import com.schedulerates.user.service.UserUpdatePasswordService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link RegisterService} for handling user registration.
 */
@Service
@RequiredArgsConstructor
public class UserUpdatePasswordServiceImpl implements UserUpdatePasswordService {

    private final UserRepository userRepository;

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    private final PasswordEncoder passwordEncoder;

    /**
     * Updates the password of a user identified by its unique ID using the provided update request.
     *
     * <p>This method checks if the old password is correct, encodes the new password, updates the user entity in the database,
     * and returns the updated user.</p>
     *
     * @param userId the ID of the user to update.
     * @param userUpdatePasswordRequest the request containing the old and new password.
     * @return the updated {@link User}.
     * @throws UserNotFoundException if no user with the given ID exists.
     * @throws PasswordNotValidException if the old password is incorrect.
     */
    @Override
    public User updatePasswordById(String userId, UserUpdatePasswordRequest userUpdatePasswordRequest) {

        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!passwordEncoder.matches(userUpdatePasswordRequest.getOldPassword(), userEntity.getPassword())) {
            throw new PasswordNotValidException("The old password is incorrect.");
        }

        userEntity.setPassword(passwordEncoder.encode(userUpdatePasswordRequest.getNewPassword()));

        UserEntity updatedUserEntity = userRepository.save(userEntity);

        return userEntityToUserMapper.map(updatedUserEntity);
    }

/**
 * Resets the password of a user identified by its unique ID.
 *
 * <p>This method encodes the new password, updates the user entity in the database,
 * and returns the updated user.</p>
 *
 * @param userId the ID of the user whose password is to be reset.
 * @param userUpdatePasswordRequest the request containing the new password.
 * @return the updated {@link User}.
 * @throws UserNotFoundException if no user with the given ID exists.
 */

    @Override
    public User resetUserPassword(String userId, UserResetPasswordRequest userResetPasswordRequest) {

        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        userEntity.setPassword(passwordEncoder.encode(userResetPasswordRequest.getNewPassword()));

        UserEntity updatedUserEntity = userRepository.save(userEntity);

        return userEntityToUserMapper.map(updatedUserEntity);
    }
}
