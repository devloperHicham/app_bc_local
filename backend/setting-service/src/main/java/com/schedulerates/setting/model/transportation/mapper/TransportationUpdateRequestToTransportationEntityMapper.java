package com.schedulerates.setting.model.transportation.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.transportation.dto.request.TransportationUpdateRequest;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link TransportationUpdateRequestToTransportationEntityMapper} for updating {@link TransportationEntity} using {@link TransportationUpdateRequest}.
 */
@Mapper
public interface TransportationUpdateRequestToTransportationEntityMapper extends BaseMapper<TransportationUpdateRequest, TransportationEntity> {

    /**
     * Maps fields from TransportationUpdateRequest to update TransportationEntity.
     *
     * @param transportationEntityToBeUpdate The TransportationEntity object to be updated.
     * @param transportationUpdateRequest    The TransportationUpdateRequest object containing updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(TransportationEntity transportationEntityToBeUpdate, TransportationUpdateRequest transportationUpdateRequest) {
        transportationEntityToBeUpdate.setTransportationName(transportationUpdateRequest.getTransportationName());
        transportationEntityToBeUpdate.setObs(transportationUpdateRequest.getObs());
    }

    /**
     * Initializes and returns an instance of TransportationUpdateRequestToTransportationEntityMapper.
     *
     * @return Initialized TransportationUpdateRequestToTransportationEntityMapper instance.
     */
    static TransportationUpdateRequestToTransportationEntityMapper initialize() {
        return Mappers.getMapper(TransportationUpdateRequestToTransportationEntityMapper.class);
    }

}
