package com.schedulerates.setting.model.transportation.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link TransportationEntityToTransportationMapper} for converting {@link TransportationEntity} to {@link Transportation}.
 */
@Mapper
public interface TransportationEntityToTransportationMapper extends BaseMapper<TransportationEntity, Transportation> {

    /**
     * Maps TransportationEntity to Transportation.
     *
     * @param source The TransportationEntity object to map.
     * @return Transportation object containing mapped data.
     */
    @Override
    Transportation map(TransportationEntity source);

    /**
     * Initializes and returns an instance of TransportationEntityToTransportationMapper.
     *
     * @return Initialized TransportationEntityToTransportationMapper instance.
     */
    static TransportationEntityToTransportationMapper initialize() {
        return Mappers.getMapper(TransportationEntityToTransportationMapper.class);
    }

}
