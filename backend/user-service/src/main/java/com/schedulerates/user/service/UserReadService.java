package com.schedulerates.user.service;

import java.util.List;
import com.schedulerates.user.model.common.CustomPage;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.UserPagingRequest;

/**
 * Service interface named {@link UserReadService} for reading users.
 */
public interface UserReadService {

    /**
     * Retrieves a user by its unique ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User object corresponding to the given ID.
     */
    User getUserById(final String userId);

    /**
     * Retrieves all users.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User object corresponding to the given ID.
     */
    List<User> getAllUsers();

    /**
     * Retrieves a page of users based on the paging request criteria.
     *
     * @param userPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of users that match the paging
     *         criteria.
     */
    CustomPage<User> getUsers(final UserPagingRequest userPagingRequest);

    /**
     * Retrieves a user by its email address.
     *
     * @param userEmail The email address of the user to retrieve.
     * @return The User object corresponding to the given email address.
     */
    List<User> getUsersByEmails(final List<String> userEmails);
}