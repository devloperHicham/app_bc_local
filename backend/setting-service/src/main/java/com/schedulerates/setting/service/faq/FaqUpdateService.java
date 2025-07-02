package com.schedulerates.setting.service.faq;

import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqUpdateRequest;

/**
 * Service interface named {@link FaqUpdateService} for updating FAQs.
 */
public interface FaqUpdateService {

    /**
     * Updates a FAQ identified by its unique ID using the provided update request.
     *
     * @param faqId           The ID of the FAQ to update.
     * @param faqUpdateRequest The request containing updated data for the FAQ.
     * @return The updated FAQ object.
     */
    Faq updateFaqById(final String faqId, final FaqUpdateRequest faqUpdateRequest);

}