package com.schedulerates.user.model.user.dto.response;
import com.schedulerates.user.model.user.enums.UserStatus;
import com.schedulerates.user.model.user.enums.UserType;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String companyName;
    private UserStatus userStatus;
    private String obs;
    private UserType userType;
    private String createdAt;
    private String updatedAt;
}