package com.schedulerates.setting.service.faq.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqUpdateRequest;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import com.schedulerates.setting.model.faq.mapper.FaqEntityToFaqMapper;
import com.schedulerates.setting.model.faq.mapper.FaqUpdateRequestToFaqEntityMapper;
import com.schedulerates.setting.repository.FaqRepository;
import com.schedulerates.setting.service.faq.FaqUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link FaqUpdateServiceImpl} for updating FAQs.
 */
@Service
@RequiredArgsConstructor
public class FaqUpdateServiceImpl implements FaqUpdateService {

    private final FaqRepository faqRepository;

    private final FaqUpdateRequestToFaqEntityMapper faqUpdateRequestToFaqEntityMapper =
            FaqUpdateRequestToFaqEntityMapper.initialize();

    private final FaqEntityToFaqMapper faqEntityToFaqMapper =
            FaqEntityToFaqMapper.initialize();

    /**
     * Updates a FAQ identified by its unique ID using the provided update request.
     *
     * @param faqId           The ID of the FAQ to update.
     * @param faqUpdateRequest The request containing updated data for the FAQ.
     * @return The updated FAQ object.
     * @throws FaqNotFoundException If no FAQ with the given ID exists.
     * @throws FaqAlreadyExistException If another FAQ with the updated name already exists.
     */
    @Override
    public Faq updateFaqById(String faqId, FaqUpdateRequest faqUpdateRequest) {

        final FaqEntity faqEntityToBeUpdate = faqRepository
                .findById(faqId)
                .orElseThrow(() -> new NotFoundException("With given faqID = " + faqId));

        faqUpdateRequestToFaqEntityMapper.mapForUpdating(faqEntityToBeUpdate, faqUpdateRequest);

        FaqEntity updatedFaqEntity = faqRepository.save(faqEntityToBeUpdate);

        return faqEntityToFaqMapper.map(updatedFaqEntity);

    }
}