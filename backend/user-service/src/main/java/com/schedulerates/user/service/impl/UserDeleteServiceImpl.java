package com.schedulerates.user.service.impl;

import com.schedulerates.user.exception.UserNotFoundException;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.repository.UserRepository;
import com.schedulerates.user.service.UserDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link UserDeleteServiceImpl} for deleting users.
 */
@Service
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService {

    private final UserRepository userRepository;

    /**
     * Deletes a user identified by its unique ID.
     *
     * @param userId The ID of the user to delete.
     * @throws UserNotFoundException If no user with the given ID exists.
     */
    @Override
    public void deleteUserById(String userId) {

        final UserEntity userEntityToBeDelete = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("With given userID = " + userId));

        userRepository.delete(userEntityToBeDelete);
    }

}