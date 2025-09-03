package com.schedulerates.setting.service.notification.impl;

import com.schedulerates.setting.model.auth.enums.TokenClaims;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqPagingRequest;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import com.schedulerates.setting.model.faq.mapper.ListFaqEntityToListFaqMapper;
import com.schedulerates.setting.model.notification.dto.response.NotificationResponse;
import com.schedulerates.setting.repository.FaqRepository;
import com.schedulerates.setting.service.faq.impl.FaqReadServiceImpl;
import com.schedulerates.setting.service.notification.NotificationReadService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation named {@link FaqReadServiceImpl} for reading FAQs.
 */
@Service
@RequiredArgsConstructor
public class NotificationReadServiceImpl implements NotificationReadService {

    private final FaqRepository faqRepository;

    private final ListFaqEntityToListFaqMapper listFaqEntityToListFaqMapper = ListFaqEntityToListFaqMapper.initialize();

    private static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * Retrieves a page of FAQs based on the paging request criteria.
     *
     * @param faqPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of FAQs that match the paging
     *         criteria.
     * @throws FaqNotFoundException If no FAQs are found based on the paging
     *                              criteria.
     */
    @Override
    public CustomPage<Faq> getNotifications(FaqPagingRequest faqPagingRequest) {
        final Page<FaqEntity> faqEntityPage;

        String currentUserEmail = getCurrentUserEmail();

        boolean isAdmin = isAdmin();

        faqEntityPage = isAdmin
                ? faqRepository.findAllOperatorAndActive(currentUserEmail, faqPagingRequest.toPageable())
                : faqRepository.findByFiltersAndOperators(currentUserEmail,
                        faqPagingRequest.toPageable());

        final List<Faq> faqDomainModels = listFaqEntityToListFaqMapper
                .toFaqList(faqEntityPage.getContent());

        return CustomPage.of(faqDomainModels, faqEntityPage);

    }

    /**
     * Retrieves the count of unread notifications for the current user.
     *
     * @return The number of unread notifications associated with the current user.
     */

    @Override
    public NotificationResponse getNotificationCounts() {
        String currentUserEmail = getCurrentUserEmail();
        long count = faqRepository.countByReadFalseAndCreatedBy(currentUserEmail);
        return NotificationResponse.builder()
                .notifications((int) count)
                .build();
    }

    private String getCurrentUserEmail() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !ANONYMOUS_USER.equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USER_EMAIL.getValue()).toString())
                .orElse(ANONYMOUS_USER);
    }

    private boolean isAdmin() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !ANONYMOUS_USER.equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USER_TYPE.getValue()).toString())
                .orElse(ANONYMOUS_USER)
                .equalsIgnoreCase("ADMIN");
    }
}