package com.schedulerates.setting.controller;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.common.dto.response.CustomResponse;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqCreateRequest;
import com.schedulerates.setting.model.faq.dto.request.FaqPagingRequest;
import com.schedulerates.setting.model.faq.dto.request.FaqUpdateRequest;
import com.schedulerates.setting.model.faq.dto.response.FaqResponse;
import com.schedulerates.setting.model.faq.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.setting.model.faq.mapper.FaqToFaqResponseMapper;
import com.schedulerates.setting.service.faq.FaqCreateService;
import com.schedulerates.setting.service.faq.FaqDeleteService;
import com.schedulerates.setting.service.faq.FaqReadService;
import com.schedulerates.setting.service.faq.FaqUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link FaqController} for managing FAQs.
 * Provides endpoints to create, read, update, and delete FAQs.
 */
@RestController
@RequestMapping("/api/v1/settings/faqs")
@RequiredArgsConstructor
@Validated
public class FaqController {

    private final FaqCreateService faqCreateService;
    private final FaqReadService faqReadService;
    private final FaqUpdateService faqUpdateService;
    private final FaqDeleteService faqDeleteService;

    private final FaqToFaqResponseMapper faqToFaqResponseMapper = FaqToFaqResponseMapper.initialize();

    private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
            .initialize();

    /**
     * Creates a new FAQ.
     *
     * @param faqCreateRequest the request payload containing FAQ details
     * @return a {@link CustomResponse} containing the ID of the created FAQ
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<String> createFaq(@RequestBody @Valid final FaqCreateRequest faqCreateRequest) {

        final Faq createdFaq = faqCreateService
                .createFaq(faqCreateRequest);

        return CustomResponse.successOf(createdFaq.getId());
    }

    /**
     * Retrieves a FAQ by its ID.
     *
     * @param faqId the ID of the FAQ to retrieve
     * @return a {@link CustomResponse} containing the FAQ details
     */
    @GetMapping("/{faqId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<FaqResponse> getFaqById(@PathVariable @UUID final String faqId) {

        final Faq faq = faqReadService.getFaqById(faqId);

        final FaqResponse faqResponse = faqToFaqResponseMapper.map(faq);

        return CustomResponse.successOf(faqResponse);

    }

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

        final CustomPage<Faq> faqPage = faqReadService.getFaqs(faqPagingRequest);

        final CustomPagingResponse<FaqResponse> faqPagingResponse = customPageToCustomPagingResponseMapper
                .toPagingResponse(faqPage);

        return CustomResponse.successOf(faqPagingResponse);

    }

    /**
     * Updates an existing FAQ by its ID.
     *
     * @param faqUpdateRequest the request payload containing updated FAQ details
     * @param faqId            the ID of the FAQ to update
     * @return a {@link CustomResponse} containing the updated FAQ details
     */
    @PutMapping("/{faqId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<FaqResponse> updatedFaqById(
            @RequestBody @Valid final FaqUpdateRequest faqUpdateRequest,
            @PathVariable @UUID final String faqId) {

        final Faq updatedFaq = faqUpdateService.updateFaqById(faqId, faqUpdateRequest);

        final FaqResponse faqResponse = faqToFaqResponseMapper.map(updatedFaq);

        return CustomResponse.successOf(faqResponse);
    }

    /**
     * Deletes a FAQ by its ID.
     *
     * @param faqId the ID of the FAQ to delete
     * @return a {@link CustomResponse} indicating successful deletion
     */
    @DeleteMapping("/{faqId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<Void> deleteFaqById(@PathVariable @UUID final String faqId) {

        faqDeleteService.deleteFaqById(faqId);
        return CustomResponse.SUCCESS;
    }
}