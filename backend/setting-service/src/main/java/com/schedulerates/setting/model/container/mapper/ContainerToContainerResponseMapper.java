package com.schedulerates.setting.model.container.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.response.ContainerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ContainerToContainerResponseMapper} for converting {@link Container} to {@link ContainerResponse}.
 */
@Mapper
public interface ContainerToContainerResponseMapper extends BaseMapper<Container, ContainerResponse> {

    /**
     * Maps Container to ContainerResponse.
     *
     * @param source The Container object to map.
     * @return ContainerResponse object containing mapped data.
     */
    @Override
    ContainerResponse map(Container source);

    /**
     * Initializes and returns an instance of ContainerToContainerResponseMapper.
     *
     * @return Initialized ContainerToContainerResponseMapper instance.
     */
    static ContainerToContainerResponseMapper initialize() {
        return Mappers.getMapper(ContainerToContainerResponseMapper.class);
    }

}