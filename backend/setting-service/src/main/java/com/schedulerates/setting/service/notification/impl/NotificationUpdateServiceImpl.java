package com.schedulerates.setting.service.notification.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schedulerates.setting.model.auth.enums.TokenClaims;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import com.schedulerates.setting.repository.FaqRepository;
import com.schedulerates.setting.service.notification.NotificationUpdateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationUpdateServiceImpl implements NotificationUpdateService {

    private static final String ANONYMOUS_USER = "anonymousUser";
    private final FaqRepository faqRepository;

    @Override
    @Transactional
    public void updateNotificationStatus() {
        String currentUserEmail = getCurrentUserEmail();

        List<FaqEntity> unreadFaqs = faqRepository.findByReadFalseAndCreatedBy(currentUserEmail);

        for (FaqEntity faq : unreadFaqs) {
            faq.setRead(true); // make sure this setter exists
        }

        faqRepository.saveAll(unreadFaqs);
    }

    private String getCurrentUserEmail() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !ANONYMOUS_USER.equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USER_EMAIL.getValue()).toString())
                .orElse(ANONYMOUS_USER);
    }
}
