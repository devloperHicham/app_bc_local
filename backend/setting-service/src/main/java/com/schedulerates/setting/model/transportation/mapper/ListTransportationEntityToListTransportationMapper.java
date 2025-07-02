package com.schedulerates.setting.model.transportation.mapper;

import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListTransportationEntityToListTransportationMapper} for converting {@link List<TransportationEntity>} to {@link List<Transportation>}.
 */
@Mapper
public interface ListTransportationEntityToListTransportationMapper {

    TransportationEntityToTransportationMapper transportationEntityToTransportationMapper = Mappers.getMapper(TransportationEntityToTransportationMapper.class);

    /**
     * Converts a list of TransportationEntity objects to a list of Transportation objects.
     *
     * @param TransportationEntities The list of TransportationEntity objects to convert.
     * @return List of Transportation objects containing mapped data.
     */
    default List<Transportation> toTransportationList(List<TransportationEntity> transportationEntities) {

        if (transportationEntities == null) {
            return null;
        }

        return transportationEntities.stream()
                .map(transportationEntityToTransportationMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListTransportationEntityToListTransportationMapper.
     *
     * @return Initialized ListTransportationEntityToListTransportationMapper instance.
     */
    static ListTransportationEntityToListTransportationMapper initialize() {
        return Mappers.getMapper(ListTransportationEntityToListTransportationMapper.class);
    }

}
