package com.schedulerates.setting.service.faq.impl;

import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqCreateRequest;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import com.schedulerates.setting.model.faq.mapper.FaqCreateRequestToFaqEntityMapper;
import com.schedulerates.setting.model.faq.mapper.FaqEntityToFaqMapper;
import com.schedulerates.setting.repository.FaqRepository;
import com.schedulerates.setting.service.faq.FaqCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link FaqCreateServiceImpl} for creating FAQs.
 */
@Service
@RequiredArgsConstructor
public class FaqCreateServiceImpl implements FaqCreateService {

    private final FaqRepository faqRepository;

    private final FaqCreateRequestToFaqEntityMapper faqCreateRequestToFaqEntityMapper =
            FaqCreateRequestToFaqEntityMapper.initialize();

    private final FaqEntityToFaqMapper faqEntityToFaqMapper = FaqEntityToFaqMapper.initialize();

    /**
     * Creates a new FAQ based on the provided FAQ creation request.
     *
     * @param faqCreateRequest The request containing data to create the FAQ.
     * @return The created FAQ object.
     * @throws FaqAlreadyExistException If a FAQ with the same question already exists.
     */
    @Override
    public Faq createFaq(FaqCreateRequest faqCreateRequest) {

        final FaqEntity faqEntityToBeSave = faqCreateRequestToFaqEntityMapper.mapForSaving(faqCreateRequest);

        FaqEntity savedFaqEntity = faqRepository.save(faqEntityToBeSave);

        return faqEntityToFaqMapper.map(savedFaqEntity);

    }

}