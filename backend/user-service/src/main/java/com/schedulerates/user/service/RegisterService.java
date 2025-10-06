package com.schedulerates.user.service;

import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.RegisterRequest;

/**
 * Service interface named {@link RegisterService} for user registration operations.
 */
public interface RegisterService {

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest the request containing user registration details.
     * @return the registered {@link User} instance.
     */
    User registerUser(final RegisterRequest registerRequest);

        /**
     * Activates a user account using the activation token.
     *
     * @param activationToken the activation token
     * @return true if activation was successful
     */
    boolean activateUser(final String activationToken);

    /**
     * Resends the activation email to the user.
     *
     * @param email the user's email
     */
    void resendActivationEmail(final String email);
}
