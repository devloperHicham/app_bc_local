package com.schedulerates.user.service;

/**
 * Service interface named {@link UserDeleteService} for deleting users.
 */
public interface UserDeleteService {

    /**
     * Deletes a user identified by its unique ID.
     *
     * @param userId The ID of the user to delete.
     */
    void deleteUserById(final String userId);

}