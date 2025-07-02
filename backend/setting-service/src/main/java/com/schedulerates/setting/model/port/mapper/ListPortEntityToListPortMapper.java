package com.schedulerates.setting.model.port.mapper;

import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.entity.PortEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListPortEntityToListPortMapper} for converting {@link List<PortEntity>} to {@link List<Port>} .
 */
@Mapper
public interface ListPortEntityToListPortMapper {

    PortEntityToPortMapper portEntityToPortMapper = Mappers.getMapper(PortEntityToPortMapper.class);

    /**
     * Converts a list of PortEntity objects to a list of Port objects.
     *
     * @param portEntities The list of PortEntity objects to convert.
     * @return List of Port objects containing mapped data.
     */
    default List<Port> toPortList(List<PortEntity> portEntities) {

        if (portEntities == null) {
            return null;
        }

        return portEntities.stream()
                .map(portEntityToPortMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListPortEntityToListPortMapper.
     *
     * @return Initialized ListPortEntityToListPortMapper instance.
     */
    static ListPortEntityToListPortMapper initialize() {
        return Mappers.getMapper(ListPortEntityToListPortMapper.class);
    }

}
