package com.schedulerates.user.service.impl;

import com.schedulerates.user.exception.PasswordNotValidException;
import com.schedulerates.user.exception.UserNotFoundException;
import com.schedulerates.user.exception.UserStatusNotValidException;
import com.schedulerates.user.model.user.Token;
import com.schedulerates.user.model.user.dto.request.LoginRequest;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.enums.UserStatus;
import com.schedulerates.user.repository.UserRepository;
import com.schedulerates.user.service.TokenService;
import com.schedulerates.user.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserLoginService} for handling user login operations.
 * This service handles user authentication by validating login credentials and generating JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    /**
     * Authenticates a user based on the provided login request and generates a JWT token upon successful login.
     * This method retrieves the user entity from the database using the email provided in the login request.
     * It then validates the provided password against the stored password. If the credentials are valid,
     * it generates and returns a JWT token containing the user's claims.
     *
     * @param loginRequest the {@link LoginRequest} object containing the user's email and password.
     * @return a {@link Token} object containing the generated JWT token.
     * @throws UserNotFoundException if no user is found with the given email.
     * @throws PasswordNotValidException if the provided password does not match the stored password.
     */
    @Override
    public Token login(LoginRequest loginRequest) {

        final UserEntity userEntityFromDB = userRepository
                .findUserEntityByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("Can't find with given email: "
                                + loginRequest.getEmail())
                );
        // Check if user account is active
        validateUserStatus(userEntityFromDB);

        if (Boolean.FALSE.equals(passwordEncoder.matches(
                loginRequest.getPassword(), userEntityFromDB.getPassword()))) {
            throw new PasswordNotValidException();
        }

        return tokenService.generateToken(userEntityFromDB.getClaims());

    }

     /**
     * Validates the user's status before allowing login.
     *
     * @param userEntity the user entity to validate
     * @throws UserStatusNotValidException if the user status is not ACTIVE
     */
    private void validateUserStatus(UserEntity userEntity) {
        UserStatus status = userEntity.getUserStatus();
        
        switch (status) {
            case ACTIVE:
                return; // User is active, allow login
            case PENDING:
                throw new UserStatusNotValidException("Account not activated. Please check your email for activation link.");
            case PASSIVE:
                throw new UserStatusNotValidException("Account is passive. Please contact support.");
            case INACTIVE:
                throw new UserStatusNotValidException("Account is inactive. Please contact administrator.");
            case SUSPENDED:
                throw new UserStatusNotValidException("Account is suspended. Please contact administrator.");
            case EXPIRED:
                throw new UserStatusNotValidException("Activation token has expired. Please request a new activation link.");
            default:
                throw new UserStatusNotValidException("Account status is invalid: " + status);
        }
    }

}
