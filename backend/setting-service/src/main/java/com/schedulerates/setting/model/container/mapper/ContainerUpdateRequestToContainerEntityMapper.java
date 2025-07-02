package com.schedulerates.setting.model.container.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.container.dto.request.ContainerUpdateRequest;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ContainerUpdateRequestToContainerEntityMapper} for updating {@link ContainerEntity} using {@link ContainerUpdateRequest}.
 */
@Mapper
public interface ContainerUpdateRequestToContainerEntityMapper extends BaseMapper<ContainerUpdateRequest, ContainerEntity> {

    /**
     * Maps fields from ContainerUpdateRequest to update ContainerEntity.
     *
     * @param containerEntityToBeUpdate The containerEntity object to be updated.
     * @param containerUpdateRequest    The containerUpdateRequest object containing updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(ContainerEntity containerEntityToBeUpdate, ContainerUpdateRequest containerUpdateRequest) {
        containerEntityToBeUpdate.setContainerName(containerUpdateRequest.getContainerName());
        containerEntityToBeUpdate.setContainerWeight(containerUpdateRequest.getContainerWeight());
        containerEntityToBeUpdate.setObs(containerUpdateRequest.getObs());
    }

    /**
     * Initializes and returns an instance of containerUpdateRequestTocontainerEntityMapper.
     *
     * @return Initialized containerUpdateRequestTocontainerEntityMapper instance.
     */
    static ContainerUpdateRequestToContainerEntityMapper initialize() {
        return Mappers.getMapper(ContainerUpdateRequestToContainerEntityMapper.class);
    }

}