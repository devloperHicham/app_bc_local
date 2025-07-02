package com.schedulerates.auth.controller;

import com.schedulerates.auth.model.auth.dto.request.LoginRequest;
import com.schedulerates.auth.model.auth.dto.request.RegisterRequest;
import com.schedulerates.auth.model.auth.dto.request.TokenInvalidateRequest;
import com.schedulerates.auth.model.auth.dto.request.TokenRefreshRequest;
import com.schedulerates.auth.model.auth.dto.response.TokenResponse;
import com.schedulerates.auth.model.common.dto.response.CustomResponse;
import com.schedulerates.auth.service.LogoutService;
import com.schedulerates.auth.service.RefreshTokenService;
import com.schedulerates.auth.service.RegisterService;
import com.schedulerates.auth.service.UserLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller named {@link AuthController} for handling authentication-related operations.
 * This includes user registration, login, token refresh, and logout.
 */
@RestController
@RequestMapping("/api/v1/authentication/users")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final RegisterService registerService;

    private final UserLoginService userLoginService;

    private final RefreshTokenService refreshTokenService;

    private final LogoutService logoutService;

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest the registration request containing user details
     * @return a response indicating success
     */
    @PostMapping("/register")
    public CustomResponse<Void> registerAdmin(@RequestBody @Valid final RegisterRequest registerRequest) {
        registerService.registerUser(registerRequest);
        return CustomResponse.SUCCESS;
    }

    /**
     * Logs in a user with the provided login request.
     *
     * @param loginRequest the login request containing user credentials
     * @return the token response containing access and refresh tokens
     */
    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest) {
       return userLoginService.login(loginRequest);
    }

    /**
     * Refreshes the access token using the provided token refresh request.
     *
     * @param tokenRefreshRequest the token refresh request containing the refresh token
     * @return the token response containing new access and refresh tokens
     */
    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest) {
        return refreshTokenService.refreshToken(tokenRefreshRequest);
    }

    /**
     * Logs out a user by invalidating the provided token.
     *
     * @param tokenInvalidateRequest the token invalidate request containing the token to be invalidated
     * @return a response indicating success
     */
    @PostMapping("/logout")
    public CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest) {
        logoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

}