package com.schedulerates.setting.service.faq;

import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqCreateRequest;

/**
 * Service interface named {@link FaqCreateService} for creating FAQs.
 */
public interface FaqCreateService {

    /**
     * Creates a new FAQ based on the provided FAQ creation request.
     *
     * @param faqCreateRequest The request containing data to create the FAQ.
     * @return The created FAQ object.
     */
    Faq createFaq(final FaqCreateRequest faqCreateRequest);

}