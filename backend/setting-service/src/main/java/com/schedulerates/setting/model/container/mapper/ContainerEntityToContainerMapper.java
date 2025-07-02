package com.schedulerates.setting.model.container.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ContainerEntityToContainerMapper} for converting {@link ContainerEntity} to {@link Container}.
 */
@Mapper
public interface ContainerEntityToContainerMapper extends BaseMapper<ContainerEntity, Container> {

    /**
     * Maps ContainerEntity to Container.
     *
     * @param source The ContainerEntity object to map.
     * @return Container object containing mapped data.
     */
    @Override
    Container map(ContainerEntity source);

    /**
     * Initializes and returns an instance of ContainerEntityToContainerMapper.
     *
     * @return Initialized ContainerEntityToContainerMapper instance.
     */
    static ContainerEntityToContainerMapper initialize() {
        return Mappers.getMapper(ContainerEntityToContainerMapper.class);
    }

}