package com.schedulerates.user.service.impl;

import com.schedulerates.user.exception.UserAlreadyExistException;
import com.schedulerates.user.exception.UserNotFoundException;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.RegisterRequest;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.enums.UserStatus;
import com.schedulerates.user.model.user.mapper.RegisterRequestToUserEntityMapper;
import com.schedulerates.user.model.user.mapper.UserEntityToUserMapper;
import com.schedulerates.user.repository.UserRepository;
import com.schedulerates.user.service.ActivationTokenService;
import com.schedulerates.user.service.EmailService;
import com.schedulerates.user.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of {@link RegisterService} for handling user registration.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ActivationTokenService activationTokenService;
    private final PasswordEncoder passwordEncoder;

    private final RegisterRequestToUserEntityMapper registerRequestToUserEntityMapper = RegisterRequestToUserEntityMapper.initialize();
    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    /**
     * Registers a new user based on the provided {@link RegisterRequest}.
     *
     * <p>This method checks if the email already exists in the database, maps the registration request to a user entity,
     * encodes the user's password, saves the user entity to the database, and returns the registered user.</p>
     *
     * @param registerRequest the request containing user registration details.
     * @return the registered {@link User}.
     * @throws UserAlreadyExistException if the email is already used for another user.
     */
    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsUserEntityByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistException("The email is already used for another one : " + registerRequest.getEmail());
        }

        final UserEntity userEntityToBeSave = registerRequestToUserEntityMapper.mapForSaving(registerRequest);

        userEntityToBeSave.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userEntityToBeSave.setUserStatus(UserStatus.PENDING);
        
        // Generate activation token
        String activationToken = activationTokenService.generateActivationToken();
        userEntityToBeSave.setActivationToken(activationToken);
        userEntityToBeSave.setTokenCreatedAt(LocalDateTime.now());

        UserEntity savedUserEntity = userRepository.save(userEntityToBeSave);

        // Send activation email
        sendActivationEmail(savedUserEntity);

        return userEntityToUserMapper.map(savedUserEntity);

    }

    /**
     * Activates a user account using the activation token.
     *
     * @param activationToken the activation token
     * @return true if activation was successful
     */
    @Override
    @Transactional
    public boolean activateUser(String activationToken) {
        activationTokenService.validateActivationToken(activationToken);

        UserEntity userEntity = userRepository.findByActivationToken(activationToken)
                .orElseThrow(() -> new UserNotFoundException("Invalid activation token"));

        if (activationTokenService.isTokenExpired(userEntity.getTokenCreatedAt())) {
            userEntity.setUserStatus(UserStatus.EXPIRED);
            userRepository.save(userEntity);
            throw new UserAlreadyExistException("Activation token has expired. Please request a new activation email.");
        }

        if (userEntity.getUserStatus() == UserStatus.ACTIVE) {
            throw new UserAlreadyExistException("Account is already activated");
        }

        // Activate the user
        userEntity.setUserStatus(UserStatus.ACTIVE);
        userEntity.setActivationToken(null);
        userEntity.setTokenCreatedAt(null);
        userEntity.setActivatedAt(LocalDateTime.now());
        userRepository.save(userEntity);

        log.info("User activated successfully with email: {}", userEntity.getEmail());
        return true;
    }

    /**
     * Resends the activation email to the user.
     *
     * @param email the user's email
     */
    @Override
    @Transactional
    public void resendActivationEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (userEntity.getUserStatus() == UserStatus.ACTIVE) {
            throw new UserAlreadyExistException("Account is already activated");
        }

        // Generate new activation token
        String newActivationToken = activationTokenService.generateActivationToken();
        userEntity.setActivationToken(newActivationToken);
        userEntity.setTokenCreatedAt(LocalDateTime.now());
        userEntity.setUserStatus(UserStatus.PENDING);
        userRepository.save(userEntity);

        // Resend activation email
        sendActivationEmail(userEntity);

        log.info("Activation email resent to: {}", email);
    }

    /**
     * Sends activation email to the user.
     *
     * @param userEntity the user entity
     */
    private void sendActivationEmail(UserEntity userEntity) {
        try {
            String userName = userEntity.getFirstName() + " " + userEntity.getLastName();
            emailService.sendActivationEmail(
                userEntity.getEmail(), 
                userEntity.getActivationToken(), 
                userName
            );
        } catch (Exception e) {
            log.error("Failed to send activation email to: {}", userEntity.getEmail(), e);
            // Don't throw exception - user is created, they can request new activation email
        }
    }
}