package com.schedulerates.setting.service.faq;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqPagingRequest;

/**
 * Service interface named {@link FaqReadService} for reading FAQs.
 */
public interface FaqReadService {

    /**
     * Retrieves a FAQ by its unique ID.
     *
     * @param faqId The ID of the FAQ to retrieve.
     * @return The Faq object corresponding to the given ID.
     */
    Faq getFaqById(final String faqId);

    /**
     * Retrieves a page of FAQs based on the paging request criteria.
     *
     * @param faqPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of FAQs that match the paging criteria.
     */
    CustomPage<Faq> getFaqs(final FaqPagingRequest faqPagingRequest);

}