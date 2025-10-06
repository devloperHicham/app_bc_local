package com.schedulerates.user.model.user.enums;

/**
 * Represents the status of a user.
 * This enum defines the different states a user can have in the system.
 */
public enum UserStatus {
    PENDING,    // Waiting for email activation
    ACTIVE,     // Activated account
    PASSIVE,
    INACTIVE,   // Deactivated by admin/user
    SUSPENDED,  // Suspended by admin
    EXPIRED     // Activation token expired
}
