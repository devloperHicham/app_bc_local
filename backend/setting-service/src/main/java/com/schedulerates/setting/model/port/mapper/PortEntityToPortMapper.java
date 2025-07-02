package com.schedulerates.setting.model.port.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.entity.PortEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link PortEntityToPortMapper} for converting {@link PortEntity} to {@link Port}.
 */
@Mapper
public interface PortEntityToPortMapper extends BaseMapper<PortEntity, Port> {

    /**
     * Maps PortEntity to Port.
     *
     * @param source The PortEntity object to map.
     * @return Port object containing mapped data.
     */
    @Override
    Port map(PortEntity source);

    /**
     * Initializes and returns an instance of PortEntityToPortMapper.
     *
     * @return Initialized PortEntityToPortMapper instance.
     */
    static PortEntityToPortMapper initialize() {
        return Mappers.getMapper(PortEntityToPortMapper.class);
    }

}
