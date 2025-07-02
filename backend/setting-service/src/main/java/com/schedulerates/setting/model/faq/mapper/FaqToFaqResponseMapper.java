package com.schedulerates.setting.model.faq.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.response.FaqResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqToFaqResponseMapper} for converting {@link Faq} to {@link FaqResponse}.
 */
@Mapper
public interface FaqToFaqResponseMapper extends BaseMapper<Faq, FaqResponse> {

    /**
     * Maps Faq to FaqResponse.
     *
     * @param source The Faq object to map.
     * @return FaqResponse object containing mapped data.
     */
    @Override
    FaqResponse map(Faq source);

    /**
     * Initializes and returns an instance of FaqToFaqResponseMapper.
     *
     * @return Initialized FaqToFaqResponseMapper instance.
     */
    static FaqToFaqResponseMapper initialize() {
        return Mappers.getMapper(FaqToFaqResponseMapper.class);
    }

}