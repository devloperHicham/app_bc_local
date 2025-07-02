package com.schedulerates.setting.model.port.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.response.PortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link PortToPortResponseMapper} for converting {@link Port} to {@link PortResponse}.
 */
@Mapper
public interface PortToPortResponseMapper extends BaseMapper<Port, PortResponse> {

    /**
     * Maps Port to PortResponse.
     *
     * @param source The Port object to map.
     * @return PortResponse object containing mapped data.
     */
    @Override
    PortResponse map(Port source);

    /**
     * Initializes and returns an instance of PortToPortResponseMapper.
     *
     * @return Initialized PortToPortResponseMapper instance.
     */
    static PortToPortResponseMapper initialize() {
        return Mappers.getMapper(PortToPortResponseMapper.class);
    }

}
