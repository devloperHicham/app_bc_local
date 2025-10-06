package com.schedulerates.user.service;

import java.time.LocalDateTime;

public interface ActivationTokenService {
    String generateActivationToken();
    boolean isTokenExpired(LocalDateTime tokenCreatedAt);
    LocalDateTime getTokenExpiryTime();
    void validateActivationToken(String token);
}