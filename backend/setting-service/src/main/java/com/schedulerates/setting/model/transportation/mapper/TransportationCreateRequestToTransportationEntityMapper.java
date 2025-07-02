package com.schedulerates.setting.model.transportation.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.transportation.dto.request.TransportationCreateRequest;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link TransportationCreateRequestToTransportationEntityMapper} for converting {@link TransportationCreateRequest} to {@link TransportationEntity}.
 */
@Mapper
public interface TransportationCreateRequestToTransportationEntityMapper extends BaseMapper<TransportationCreateRequest, TransportationEntity> {

    /**
     * Maps TransportationCreateRequest to TransportationEntity for saving purposes.
     *
     * @param transportationCreateRequest The TransportationCreateRequest object to map.
     * @return TransportationEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default TransportationEntity mapForSaving(TransportationCreateRequest transportationCreateRequest) {
        return TransportationEntity.builder()
                .transportationName(transportationCreateRequest.getTransportationName())
                .obs(transportationCreateRequest.getObs())
                .build();
    }

    /**
     * Initializes and returns an instance of TransportationCreateRequestToTransportationEntityMapper.
     *
     * @return Initialized TransportationCreateRequestToTransportationEntityMapper instance.
     */
    static TransportationCreateRequestToTransportationEntityMapper initialize() {
        return Mappers.getMapper(TransportationCreateRequestToTransportationEntityMapper.class);
    }

}
