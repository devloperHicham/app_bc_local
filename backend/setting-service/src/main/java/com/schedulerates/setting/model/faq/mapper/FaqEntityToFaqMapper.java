package com.schedulerates.setting.model.faq.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqEntityToFaqMapper} for converting {@link FaqEntity} to {@link Faq}.
 */
@Mapper
public interface FaqEntityToFaqMapper extends BaseMapper<FaqEntity, Faq> {

    /**
     * Maps FaqEntity to Faq.
     *
     * @param source The FaqEntity object to map.
     * @return Faq object containing mapped data.
     */
    @Override
    Faq map(FaqEntity source);

    /**
     * Initializes and returns an instance of FaqEntityToFaqMapper.
     *
     * @return Initialized FaqEntityToFaqMapper instance.
     */
    static FaqEntityToFaqMapper initialize() {
        return Mappers.getMapper(FaqEntityToFaqMapper.class);
    }

}