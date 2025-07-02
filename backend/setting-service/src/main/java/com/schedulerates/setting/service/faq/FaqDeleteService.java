package com.schedulerates.setting.service.faq;

/**
 * Service interface named {@link FaqDeleteService} for deleting FAQs.
 */
public interface FaqDeleteService {

    /**
     * Deletes a FAQ identified by its unique ID.
     *
     * @param faqId The ID of the FAQ to delete.
     */
    void deleteFaqById(final String faqId);

}