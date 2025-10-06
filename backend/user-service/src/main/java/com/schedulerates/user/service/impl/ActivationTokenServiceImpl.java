package com.schedulerates.user.service.impl;

import com.schedulerates.user.service.ActivationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ActivationTokenServiceImpl implements ActivationTokenService {

    @Value("${app.activation.token.expiry-hours:24}")
    private int tokenExpiryHours;

    @Override
    public String generateActivationToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean isTokenExpired(LocalDateTime tokenCreatedAt) {
        if (tokenCreatedAt == null) {
            return true;
        }
        return tokenCreatedAt.plusHours(tokenExpiryHours).isBefore(LocalDateTime.now());
    }

    @Override
    public LocalDateTime getTokenExpiryTime() {
        return LocalDateTime.now().plusHours(tokenExpiryHours);
    }

    @Override
    public void validateActivationToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Activation token cannot be null or empty");
        }
    }
}