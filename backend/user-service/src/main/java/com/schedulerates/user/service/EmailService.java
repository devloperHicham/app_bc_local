
package com.schedulerates.user.service;

public interface EmailService {
    void sendActivationEmail(String to, String activationUrl, String userName);
}