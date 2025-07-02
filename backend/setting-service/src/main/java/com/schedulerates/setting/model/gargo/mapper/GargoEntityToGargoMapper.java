package com.schedulerates.setting.model.gargo.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqEntityToFaqMapper} for converting {@link FaqEntity} to {@link Transportation}.
 */
@Mapper
public interface GargoEntityToGargoMapper extends BaseMapper<GargoEntity, Gargo> {

    /**
     * Maps GargoEntity to Gargo.
     *
     * @param source The GargoEntity object to map.
     * @return Gargo object containing mapped data.
     */
    @Override
    Gargo map(GargoEntity source);

    /**
     * Initializes and returns an instance of GargoEntityToGargoMapper.
     *
     * @return Initialized GargoEntityToGargoMapper instance.
     */
    static GargoEntityToGargoMapper initialize() {
        return Mappers.getMapper(GargoEntityToGargoMapper.class);
    }

}
