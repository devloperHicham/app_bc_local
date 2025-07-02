package com.schedulerates.setting.service.notification;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqPagingRequest;
import com.schedulerates.setting.service.faq.FaqReadService;

/**
 * Service interface named {@link FaqReadService} for reading FAQs.
 */
public interface NotificationReadService {

    /**
     * Retrieves a page of FAQs based on the paging request criteria.
     *
     * @param faqPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of FAQs that match the paging criteria.
     */
    CustomPage<Faq> getNotifications(final FaqPagingRequest faqPagingRequest);

}