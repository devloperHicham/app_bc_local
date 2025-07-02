package com.schedulerates.setting.service.faq.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.auth.enums.TokenClaims;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.request.FaqPagingRequest;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import com.schedulerates.setting.model.faq.mapper.ListFaqEntityToListFaqMapper;
import com.schedulerates.setting.model.faq.mapper.FaqEntityToFaqMapper;
import com.schedulerates.setting.repository.FaqRepository;
import com.schedulerates.setting.service.faq.FaqReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation named {@link FaqReadServiceImpl} for reading FAQs.
 */
@Service
@RequiredArgsConstructor
public class FaqReadServiceImpl implements FaqReadService {

    private final FaqRepository faqRepository;

    private final FaqEntityToFaqMapper faqEntityToFaqMapper = FaqEntityToFaqMapper.initialize();

    private final ListFaqEntityToListFaqMapper listFaqEntityToListFaqMapper = ListFaqEntityToListFaqMapper.initialize();

    private static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * Retrieves a FAQ by its unique ID.
     *
     * @param faqId The ID of the FAQ to retrieve.
     * @return The FAQ object corresponding to the given ID.
     * @throws FaqNotFoundException If no FAQ with the given ID exists.
     */
    @Override
    public Faq getFaqById(String faqId) {

        final FaqEntity faqEntityFromDB = faqRepository
                .findById(faqId)
                .orElseThrow(() -> new NotFoundException("With given faqID = " + faqId));

        return faqEntityToFaqMapper.map(faqEntityFromDB);
    }

    /**
     * Retrieves a page of FAQs based on the paging request criteria.
     *
     * @param faqPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of FAQs that match the paging
     *         criteria.
     * @throws FaqNotFoundException If no FAQs are found based on the paging
     *                              criteria.
     */
    @Override
    public CustomPage<Faq> getFaqs(FaqPagingRequest faqPagingRequest) {
        final Page<FaqEntity> faqEntityPage;

        String currentUserEmail = getCurrentUserEmail();

        faqEntityPage = faqRepository.findByFiltersAndCreatedBy(currentUserEmail, faqPagingRequest.toPageable());

        final List<Faq> faqDomainModels = listFaqEntityToListFaqMapper
                .toFaqList(faqEntityPage.getContent());

        return CustomPage.of(faqDomainModels, faqEntityPage);

    }

    private String getCurrentUserEmail() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !ANONYMOUS_USER.equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USER_EMAIL.getValue()).toString())
                .orElse(ANONYMOUS_USER);
    }

}