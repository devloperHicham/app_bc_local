package com.schedulerates.user.controller;

import com.schedulerates.user.model.common.CustomPage;
import com.schedulerates.user.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.user.model.common.dto.response.CustomResponse;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.UserPagingRequest;
import com.schedulerates.user.model.user.dto.request.UserResetPasswordRequest;
import com.schedulerates.user.model.user.dto.request.UserUpdatePasswordRequest;
import com.schedulerates.user.model.user.dto.request.UserUpdateRequest;
import com.schedulerates.user.model.user.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.user.model.user.Token;
import com.schedulerates.user.model.user.dto.request.LoginRequest;
import com.schedulerates.user.model.user.dto.request.RegisterRequest;
import com.schedulerates.user.model.user.dto.request.TokenInvalidateRequest;
import com.schedulerates.user.model.user.dto.request.TokenRefreshRequest;
import com.schedulerates.user.model.user.dto.response.TokenResponse;
import com.schedulerates.user.model.user.dto.response.UserResponse;
import com.schedulerates.user.model.user.mapper.TokenToTokenResponseMapper;
import com.schedulerates.user.model.user.mapper.UserToUserResponseMapper;
import com.schedulerates.user.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link UserController} for managing user-related
 * operations.
 * Provides endpoints for user registration, token validation, login, token
 * refresh, logout, and authentication retrieval.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final RegisterService registerService;

    private final TokenService tokenService;

    private final UserLoginService userLoginService;

    private final RefreshTokenService refreshTokenService;

    private final LogoutService logoutService;

    private final UserReadService userReadService;

    private final UserDeleteService userDeleteService;

    private final UserUpdateService userUpdateService;

    private final UserUpdatePasswordService userUpdatePasswordService;

    private final TokenToTokenResponseMapper tokenToTokenResponseMapper = TokenToTokenResponseMapper.initialize();

    private final UserToUserResponseMapper userToUserResponseMapper = UserToUserResponseMapper.initialize();

    private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
            .initialize();

    /**
     * Retrieves a paginated list of users based on the paging request.
     *
     * @param userPagingRequest the request payload containing paging information
     * @return a {@link CustomResponse} containing the paginated list of users
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<CustomPagingResponse<UserResponse>> getUsers(
            @RequestBody @Valid final UserPagingRequest userPagingRequest) {

        final CustomPage<User> userPage = userReadService.getUsers(userPagingRequest);

        final CustomPagingResponse<UserResponse> userPagingResponse = customPageToCustomPagingResponseMapper
                .toPagingResponse(userPage);

        return CustomResponse.successOf(userPagingResponse);
    }

    /**
     * Registers a new user.
     *
     * @param registerRequest the user registration request containing user details
     * @return a {@link CustomResponse} indicating the success of the registration
     */
    @PostMapping("/register")
    public CustomResponse<Void> registerUser(@RequestBody @Validated final RegisterRequest registerRequest) {
        log.info("UserController | registerUser");
        registerService.registerUser(registerRequest);
        return CustomResponse.SUCCESS;
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param userId the ID of the user to retrieve
     * @return a {@link CustomResponse} containing the user details
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<UserResponse> getUserById(@PathVariable @UUID final String userId) {

        final User user = userReadService.getUserById(userId);

        final UserResponse userResponse = userToUserResponseMapper.map(user);

        return CustomResponse.successOf(userResponse);

    }

    /**
     * Updates an existing user by its ID.
     *
     * @param userUpdateRequest the request payload containing updated user details
     * @param userId            the ID of the user to update
     * @return a {@link CustomResponse} containing the updated user details
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<UserResponse> updatedUserById(
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest,
            @PathVariable @UUID final String userId) {

        final User updatedUser = userUpdateService.updateUserById(userId, userUpdateRequest);

        final UserResponse userResponse = userToUserResponseMapper.map(updatedUser);

        return CustomResponse.successOf(userResponse);
    }

    /**
     * Updates the password of an existing user by its ID.
     *
     * @param userUpdatePasswordRequest the request payload containing the new
     *                                  password
     * @param userId                    the ID of the user to update
     * @return a {@link CustomResponse} containing the updated user details
     */
    @PutMapping("/password/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<UserResponse> updateUserPassword(
            @RequestBody @Valid final UserUpdatePasswordRequest userUpdatePasswordRequest,
            @PathVariable @UUID final String userId) {

        final User updatedUser = userUpdatePasswordService.updatePasswordById(userId, userUpdatePasswordRequest);

        final UserResponse userResponse = userToUserResponseMapper.map(updatedUser);

        return CustomResponse.successOf(userResponse);
    }

    /**
     * Deletes a user by its ID.
     *
     * @param userId the ID of the user to delete
     * @return a {@link CustomResponse} indicating successful deletion
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> deleteUserById(@PathVariable @UUID final String userId) {

        userDeleteService.deleteUserById(userId);
        return CustomResponse.SUCCESS;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<List<UserResponse>> getAllUsers() {

        List<User> users = userReadService.getAllUsers();

        List<UserResponse> responses = users.stream()
                .map(userToUserResponseMapper::map)
                .toList();
        return CustomResponse.successOf(responses);
    }

    /**
     * Validates the provided token.
     *
     * @param token the token to be validated
     * @return a {@link ResponseEntity} with an HTTP status code indicating the
     *         validation result
     */
    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestParam String token) {
        log.info("UserController | validateToken");
        tokenService.verifyAndValidate(token);
        return ResponseEntity.ok().build();
    }

    /**
     * Logs in a user and generates a new token.
     *
     * @param loginRequest the login request containing user credentials
     * @return a {@link CustomResponse} containing the generated
     *         {@link TokenResponse}
     */
    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest) {
        log.info("UserController | validateToken");
        final Token token = userLoginService.login(loginRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    /**
     * Refreshes the token based on the provided refresh request.
     *
     * @param tokenRefreshRequest the token refresh request containing the refresh
     *                            token
     * @return a {@link CustomResponse} containing the new {@link TokenResponse}
     */
    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(
            @RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest) {
        log.info("UserController | refreshToken");
        final Token token = refreshTokenService.refreshToken(tokenRefreshRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    /**
     * Logs out a user by invalidating the provided token.
     *
     * @param tokenInvalidateRequest the request containing the token to be
     *                               invalidated
     * @return a {@link CustomResponse} indicating the success of the logout
     *         operation
     */
    @PostMapping("/logout")
    public CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest) {
        log.info("UserController | logout");
        logoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

    /**
     * Retrieves the authentication details for the provided token.
     *
     * @param token the token for which to retrieve authentication details
     * @return a {@link ResponseEntity} containing the
     *         {@link UsernamePasswordAuthenticationToken} for the token
     */
    @GetMapping("/authenticate")
    public ResponseEntity<UsernamePasswordAuthenticationToken> getAuthentication(@RequestParam String token) {
        UsernamePasswordAuthenticationToken authentication = tokenService.getAuthentication(token);
        return ResponseEntity.ok(authentication);
    }

    /**
     * Resets the password of a user by its ID.
     *
     * <p>
     * This method is only accessible by the admin user.
     * </p>
     *
     * @param userId  the ID of the user to reset the password for
     * @param request the request containing the new password
     * @return a {@link CustomResponse} containing the updated user details
     */
    @PutMapping("/reset-password/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<UserResponse> resetUserPassword(
            @RequestBody @Valid final UserResetPasswordRequest userResetPasswordRequest,
            @PathVariable @UUID final String userId) {

        final User updatedUser = userUpdatePasswordService.resetUserPassword(userId, userResetPasswordRequest);

        final UserResponse userResponse = userToUserResponseMapper.map(updatedUser);

        return CustomResponse.successOf(userResponse);
    }

    /**
     * Retrieves a Gargo by its ID.
     *
     * @param gargoId the ID of the Gargo to retrieve
     * @return a {@link CustomResponse} containing the Gargo details
     */
    @GetMapping("/emails") // no path variable here
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<List<UserResponse>> getUsersByEmails(@RequestParam List<String> userEmails) {
        List<User> users = userReadService.getUsersByEmails(userEmails);
        List<UserResponse> userResponses = users.stream()
                .map(userToUserResponseMapper::map)
                .toList();

        return CustomResponse.successOf(userResponses);
    }

}
