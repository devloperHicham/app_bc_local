package com.schedulerates.user.service;

import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.UserUpdateRequest;

/**
 * Service interface named {@link UserUpdateService} for updating users.
 */
public interface UserUpdateService {

    /**
     * Updates a user identified by its unique ID using the provided update request.
     *
     * @param userId           The ID of the user to update.
     * @param userUpdateRequest The request containing updated data for the user.
     * @return The updated User object.
     */
    User updateUserById(final String userId, final UserUpdateRequest userUpdateRequest);

}