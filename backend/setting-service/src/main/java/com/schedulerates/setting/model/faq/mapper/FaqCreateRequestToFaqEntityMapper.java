package com.schedulerates.setting.model.faq.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.faq.dto.request.FaqCreateRequest;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqCreateRequestToFaqEntityMapper} for converting {@link FaqCreateRequest} to {@link FaqEntity}.
 */
@Mapper
public interface FaqCreateRequestToFaqEntityMapper extends BaseMapper<FaqCreateRequest, FaqEntity> {

    /**
     * Maps FaqCreateRequest to FaqEntity for saving purposes.
     *
     * @param faqCreateRequest The FaqCreateRequest object to map.
     * @return FaqEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default FaqEntity mapForSaving(FaqCreateRequest faqCreateRequest) {
        return FaqEntity.builder()
                .fullName(faqCreateRequest.getFullName())
                .typeFaq(faqCreateRequest.getTypeFaq())
                .obs(faqCreateRequest.getObs())
                .build();
    }

    /**
     * Initializes and returns an instance of FaqCreateRequestToFaqEntityMapper.
     *
     * @return Initialized FaqCreateRequestToFaqEntityMapper instance.
     */
    static FaqCreateRequestToFaqEntityMapper initialize() {
        return Mappers.getMapper(FaqCreateRequestToFaqEntityMapper.class);
    }

}