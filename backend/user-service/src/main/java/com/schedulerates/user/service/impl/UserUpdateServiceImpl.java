package com.schedulerates.user.service.impl;

import com.schedulerates.user.exception.UserAlreadyExistException;
import com.schedulerates.user.exception.UserNotFoundException;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.UserUpdateRequest;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.mapper.UserEntityToUserMapper;
import com.schedulerates.user.model.user.mapper.UserUpdateRequestToUserEntityMapper;
import com.schedulerates.user.repository.UserRepository;
import com.schedulerates.user.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link UserUpdateServiceImpl} for updating
 * users.
 */
@Service
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {

    private final UserRepository userRepository;

    private final UserUpdateRequestToUserEntityMapper userUpdateRequestToUserEntityMapper = UserUpdateRequestToUserEntityMapper
            .initialize();

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    /**
     * Updates a user identified by its unique ID using the provided update request.
     *
     * @param userId            The ID of the user to update.
     * @param userUpdateRequest The request containing updated data for the user.
     * @return The updated User object.
     * @throws UserNotFoundException     If no user with the given ID exists.
     * @throws UserAlreadyExistException If another user with the updated name
     *                                   already exists.
     */
    @Override
    public User updateUserById(String userId, UserUpdateRequest userUpdateRequest) {

        checkUserEmailUniqueness(userUpdateRequest.getEmail(), userId);

        final UserEntity userEntityToBeUpdate = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("With given userID = " + userId));

        userUpdateRequestToUserEntityMapper.mapForUpdating(userEntityToBeUpdate, userUpdateRequest);

        UserEntity updatedUserEntity = userRepository.save(userEntityToBeUpdate);

        return userEntityToUserMapper.map(updatedUserEntity);

    }

    /**
     * Checks if a user with the updated name already exists in the repository.
     *
     * @param userName The updated name of the user to check.
     * @throws UserAlreadyExistException If another user with the updated name
     *                                   already exists.
     */
    private void checkUserEmailUniqueness(final String email, final String userId) {
    final String normalizedEmail = email.trim().toLowerCase();
        userRepository.findUserEntityByEmail(normalizedEmail)
            .filter(user -> !user.getId().equals(userId))
            .ifPresent(user -> {
                throw new UserAlreadyExistException("With given user email = " + normalizedEmail);
            });
    }

}