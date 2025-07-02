package com.schedulerates.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.enums.UserType;
import com.schedulerates.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserSeedService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void seedAdminUser() {
        if (!userRepository.existsUserEntityByEmail("admin@admin.com")) {
            UserEntity admin = UserEntity.builder()
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin12345"))
                    .firstName("Admin")
                    .lastName("User")
                    .phoneNumber("1234567890")
                    .userType(UserType.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);
            System.out.println("✅ Admin user created.");
        } else {
            System.out.println("ℹ️ Admin user already exists.");
        }
    }
}
