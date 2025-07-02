package com.schedulerates.setting.model.gargo.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.response.GargoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqToFaqResponseMapper} for converting {@link Transportation} to {@link FaqResponse}.
 */
@Mapper
public interface GargoToGargoResponseMapper extends BaseMapper<Gargo, GargoResponse> {

    /**
     * Maps Gargo to GargoResponse.
     *
     * @param source The Gargo object to map.
     * @return GargoResponse object containing mapped data.
     */
    @Override
    GargoResponse map(Gargo source);

    /**
     * Initializes and returns an instance of GargoToGargoResponseMapper.
     *
     * @return Initialized GargoToGargoResponseMapper instance.
     */
    static GargoToGargoResponseMapper initialize() {
        return Mappers.getMapper(GargoToGargoResponseMapper.class);
    }

}
