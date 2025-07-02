package com.schedulerates.setting.model.gargo.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.gargo.dto.request.GargoCreateRequest;
import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqCreateRequestToFaqEntityMapper} for converting {@link FaqCreateRequest} to {@link FaqEntity}.
 */
@Mapper
public interface GargoCreateRequestToGargoEntityMapper extends BaseMapper<GargoCreateRequest, GargoEntity> {

    /**
     * Maps GargoCreateRequest to GargoEntity for saving purposes.
     *
     * @param gargoCreateRequest The GargoCreateRequest object to map.
     * @return GargoEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default GargoEntity mapForSaving(GargoCreateRequest gargoCreateRequest) {
        return GargoEntity.builder()
                .gargoName(gargoCreateRequest.getGargoName())
                .obs(gargoCreateRequest.getObs())
                .build();
    }

    /**
     * Initializes and returns an instance of GargoCreateRequestToGargoEntityMapper.
     *
     * @return Initialized GargoCreateRequestToGargoEntityMapper instance.
     */
    static GargoCreateRequestToGargoEntityMapper initialize() {
        return Mappers.getMapper(GargoCreateRequestToGargoEntityMapper.class);
    }

}
