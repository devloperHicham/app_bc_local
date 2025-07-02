package com.schedulerates.setting.model.container.mapper;

import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListContainerEntityToListContainerMapper} for converting {@link List<ContainerEntity>} to {@link List<Container>}.
 */
@Mapper
public interface ListContainerEntityToListContainerMapper {

    ContainerEntityToContainerMapper containerEntityToContainerMapper = Mappers.getMapper(ContainerEntityToContainerMapper.class);

    /**
     * Converts a list of ContainerEntity objects to a list of Container objects.
     *
     * @param containerEntities The list of ContainerEntity objects to convert.
     * @return List of Container objects containing mapped data.
     */
    default List<Container> toContainerList(List<ContainerEntity> containerEntities) {

        if (containerEntities == null) {
            return null;
        }

        return containerEntities.stream()
                .map(containerEntityToContainerMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListContainerEntityToListContainerMapper.
     *
     * @return Initialized ListContainerEntityToListContainerMapper instance.
     */
    static ListContainerEntityToListContainerMapper initialize() {
        return Mappers.getMapper(ListContainerEntityToListContainerMapper.class);
    }

}