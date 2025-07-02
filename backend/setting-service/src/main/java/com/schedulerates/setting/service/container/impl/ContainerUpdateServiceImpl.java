package com.schedulerates.setting.service.container.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.request.ContainerUpdateRequest;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import com.schedulerates.setting.model.container.mapper.ContainerEntityToContainerMapper;
import com.schedulerates.setting.model.container.mapper.ContainerUpdateRequestToContainerEntityMapper;
import com.schedulerates.setting.repository.ContainerRepository;
import com.schedulerates.setting.service.container.ContainerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link ContainerUpdateServiceImpl} for updating Containers.
 */
@Service
@RequiredArgsConstructor
public class ContainerUpdateServiceImpl implements ContainerUpdateService {

    private final ContainerRepository containerRepository;

    private final ContainerUpdateRequestToContainerEntityMapper containerUpdateRequestToContainerEntityMapper =
            ContainerUpdateRequestToContainerEntityMapper.initialize();

    private final ContainerEntityToContainerMapper containerEntityToContainerMapper =
            ContainerEntityToContainerMapper.initialize();

    /**
     * Updates a Container identified by its unique ID using the provided update request.
     *
     * @param ContainerId           The ID of the Container to update.
     * @param ContainerUpdateRequest The request containing updated data for the container.
     * @return The updated Container object.
     * @throws ContainerNotFoundException If no Container with the given ID exists.
     * @throws ContainerAlreadyExistException If another Container with the updated name already exists.
     */
    @Override
    public Container updateContainerById(String containerId, ContainerUpdateRequest containerUpdateRequest) {

        final ContainerEntity containerEntityToBeUpdate = containerRepository
                .findById(containerId)
                .orElseThrow(() -> new NotFoundException("With given containerID = " + containerId));

        containerUpdateRequestToContainerEntityMapper.mapForUpdating(containerEntityToBeUpdate, containerUpdateRequest);

        ContainerEntity updatedContainerEntity = containerRepository.save(containerEntityToBeUpdate);

        return containerEntityToContainerMapper.map(updatedContainerEntity);

    }
}