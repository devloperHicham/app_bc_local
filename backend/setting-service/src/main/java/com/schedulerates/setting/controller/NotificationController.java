package com.schedulerates.setting.controller;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.common.dto.response.CustomResponse;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqPagingRequest;
import com.schedulerates.setting.model.faq.dto.response.FaqResponse;
import com.schedulerates.setting.model.faq.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.setting.model.notification.dto.response.NotificationResponse;
import com.schedulerates.setting.service.notification.NotificationReadService;
import com.schedulerates.setting.service.notification.NotificationUpdateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link FaqController} for managing FAQs.
 * Provides endpoints to create, read, update, and delete FAQs.
 */
@RestController
@RequestMapping("/api/v1/settings/notifications")
@RequiredArgsConstructor
@Validated
public class NotificationController {

        private final NotificationReadService notificationReadService;

        private final NotificationUpdateService notificationUpdateService;

        private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
                        .initialize();

        /**
         * Retrieves a paginated list of FAQs based on the paging request.
         *
         * @param faqPagingRequest the request payload containing paging information
         * @return a {@link CustomResponse} containing the paginated list of FAQs
         */
        @PostMapping
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<CustomPagingResponse<FaqResponse>> getFaqs(
                        @RequestBody @Valid final FaqPagingRequest faqPagingRequest) {

                final CustomPage<Faq> faqPage = notificationReadService.getNotifications(faqPagingRequest);

                final CustomPagingResponse<FaqResponse> faqPagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(faqPage);

                return CustomResponse.successOf(faqPagingResponse);

        }

        /**
         * Updates the status of a FAQ to read.
         *
         * @return a {@link CustomResponse} indicating successful update
         */ 
        @PutMapping("/read")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<Void> updateFaqStatus() {
                notificationUpdateService.updateNotificationStatus();
                return CustomResponse.SUCCESS;
        }

        /**
         * Retrieves a NotificationResponse object containing the number of unread notifications and the total number of notifications.
         *
         * @return a {@link NotificationResponse} containing the number of unread notifications and the total number of notifications
         */
       @GetMapping("/stats")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public NotificationResponse getNotificationCounts() {
                return notificationReadService.getNotificationCounts();
        }
}