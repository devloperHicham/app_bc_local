package com.schedulerates.setting.service.faq.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import com.schedulerates.setting.repository.FaqRepository;
import com.schedulerates.setting.service.faq.FaqDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link FaqDeleteServiceImpl} for deleting FAQs.
 */
@Service
@RequiredArgsConstructor
public class FaqDeleteServiceImpl implements FaqDeleteService {

    private final FaqRepository faqRepository;

    /**
     * Deletes a FAQ identified by its unique ID.
     *
     * @param faqId The ID of the FAQ to delete.
     * @throws FaqNotFoundException If no FAQ with the given ID exists.
     */
    @Override
    public void deleteFaqById(String faqId) {

        final FaqEntity faqEntityToBeDelete = faqRepository
                .findById(faqId)
                .orElseThrow(() -> new NotFoundException("With given faqID = " + faqId));

        faqRepository.delete(faqEntityToBeDelete);

    }

}