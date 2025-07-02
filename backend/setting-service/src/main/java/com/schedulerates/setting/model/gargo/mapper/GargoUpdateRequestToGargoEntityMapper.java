package com.schedulerates.setting.model.gargo.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.gargo.dto.request.GargoUpdateRequest;
import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link FaqUpdateRequestToFaqEntityMapper} for updating {@link FaqEntity} using {@link FaqUpdateRequest}.
 */
@Mapper
public interface GargoUpdateRequestToGargoEntityMapper extends BaseMapper<GargoUpdateRequest, GargoEntity> {

    /**
     * Maps fields from GargoUpdateRequest to update GargoEntity.
     *
     * @param gargoEntityToBeUpdate The GargoEntity object to be updated.
     * @param gargoUpdateRequest    The GargoUpdateRequest object containing updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(GargoEntity gargoEntityToBeUpdate, GargoUpdateRequest gargoUpdateRequest) {
        gargoEntityToBeUpdate.setGargoName(gargoUpdateRequest.getGargoName());
        gargoEntityToBeUpdate.setObs(gargoUpdateRequest.getObs());
    }

    /**
     * Initializes and returns an instance of GargoUpdateRequestToGargoEntityMapper.
     *
     * @return Initialized GargoUpdateRequestToGargoEntityMapper instance.
     */
    static GargoUpdateRequestToGargoEntityMapper initialize() {
        return Mappers.getMapper(GargoUpdateRequestToGargoEntityMapper.class);
    }

}
