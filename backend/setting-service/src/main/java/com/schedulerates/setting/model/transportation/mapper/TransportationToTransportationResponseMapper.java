package com.schedulerates.setting.model.transportation.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.response.TransportationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link TransportationToTransportationResponseMapper} for converting {@link Transportation} to {@link TransportationResponse}.
 */
@Mapper
public interface TransportationToTransportationResponseMapper extends BaseMapper<Transportation, TransportationResponse> {

    /**
     * Maps Transportation to TransportationResponse.
     *
     * @param source The Transportation object to map.
     * @return TransportationResponse object containing mapped data.
     */
    @Override
    TransportationResponse map(Transportation source);

    /**
     * Initializes and returns an instance of TransportationToTransportationResponseMapper.
     *
     * @return Initialized TransportationToTransportationResponseMapper instance.
     */
    static TransportationToTransportationResponseMapper initialize() {
        return Mappers.getMapper(TransportationToTransportationResponseMapper.class);
    }

}
