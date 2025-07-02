package com.schedulerates.setting.service.container.impl;

import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.request.ContainerCreateRequest;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import com.schedulerates.setting.model.container.mapper.ContainerCreateRequestToContainerEntityMapper;
import com.schedulerates.setting.model.container.mapper.ContainerEntityToContainerMapper;
import com.schedulerates.setting.repository.ContainerRepository;
import com.schedulerates.setting.service.container.ContainerCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link ContainerCreateServiceImpl} for creating Containers.
 */
@Service
@RequiredArgsConstructor
public class ContainerCreateServiceImpl implements ContainerCreateService {

    private final ContainerRepository containerRepository;

    private final ContainerCreateRequestToContainerEntityMapper containerCreateRequestToContainerEntityMapper =
            ContainerCreateRequestToContainerEntityMapper.initialize();

    private final ContainerEntityToContainerMapper containerEntityToContainerMapper = ContainerEntityToContainerMapper.initialize();

    /**
     * Creates a new Container based on the provided Container creation request.
     *
     * @param ContainerCreateRequest The request containing data to create the container.
     * @return The created Container object.
     * @throws ContainerAlreadyExistException If a Container with the same question already exists.
     */
    @Override
    public Container createContainer(ContainerCreateRequest containerCreateRequest) {

        final ContainerEntity containerEntityToBeSave = containerCreateRequestToContainerEntityMapper.mapForSaving(containerCreateRequest);

        ContainerEntity savedContainerEntity = containerRepository.save(containerEntityToBeSave);

        return containerEntityToContainerMapper.map(savedContainerEntity);

    }

}