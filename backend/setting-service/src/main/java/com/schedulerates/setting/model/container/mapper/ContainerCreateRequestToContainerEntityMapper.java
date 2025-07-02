package com.schedulerates.setting.model.container.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.container.dto.request.ContainerCreateRequest;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ContainerCreateRequestToContainerEntityMapper} for converting {@link ContainerCreateRequest} to {@link ContainerEntity}.
 */
@Mapper
public interface ContainerCreateRequestToContainerEntityMapper extends BaseMapper<ContainerCreateRequest, ContainerEntity> {

    /**
     * Maps ContainerCreateRequest to ContainerEntity for saving purposes.
     *
     * @param containerCreateRequest The ContainerCreateRequest object to map.
     * @return ContainerEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default ContainerEntity mapForSaving(ContainerCreateRequest containerCreateRequest) {
        return ContainerEntity.builder()
                .containerName(containerCreateRequest.getContainerName())
                .containerWeight(containerCreateRequest.getContainerWeight())
                .obs(containerCreateRequest.getObs())
                .build();
    }

    /**
     * Initializes and returns an instance of ContainerCreateRequestToContainerEntityMapper.
     *
     * @return Initialized ContainerCreateRequestToContainerEntityMapper instance.
     */
    static ContainerCreateRequestToContainerEntityMapper initialize() {
        return Mappers.getMapper(ContainerCreateRequestToContainerEntityMapper.class);
    }

}