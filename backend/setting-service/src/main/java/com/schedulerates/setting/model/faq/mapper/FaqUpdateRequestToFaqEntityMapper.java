package com.schedulerates.setting.model.faq.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.faq.dto.request.FaqUpdateRequest;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqUpdateRequestToFaqEntityMapper} for updating {@link FaqEntity} using {@link FaqUpdateRequest}.
 */
@Mapper
public interface FaqUpdateRequestToFaqEntityMapper extends BaseMapper<FaqUpdateRequest, FaqEntity> {

    /**
     * Maps fields from FaqUpdateRequest to update FaqEntity.
     *
     * @param faqEntityToBeUpdate The FaqEntity object to be updated.
     * @param faqUpdateRequest    The FaqUpdateRequest object containing updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(FaqEntity faqEntityToBeUpdate, FaqUpdateRequest faqUpdateRequest) {
        faqEntityToBeUpdate.setFullName(faqUpdateRequest.getFullName());
        faqEntityToBeUpdate.setTypeFaq(faqUpdateRequest.getTypeFaq());
        faqEntityToBeUpdate.setObs(faqUpdateRequest.getObs());
    }

    /**
     * Initializes and returns an instance of FaqUpdateRequestToFaqEntityMapper.
     *
     * @return Initialized FaqUpdateRequestToFaqEntityMapper instance.
     */
    static FaqUpdateRequestToFaqEntityMapper initialize() {
        return Mappers.getMapper(FaqUpdateRequestToFaqEntityMapper.class);
    }

}