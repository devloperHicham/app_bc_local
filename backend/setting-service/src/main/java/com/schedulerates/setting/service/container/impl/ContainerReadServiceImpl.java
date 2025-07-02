package com.schedulerates.setting.service.container.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.request.ContainerPagingRequest;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import com.schedulerates.setting.model.container.mapper.ListContainerEntityToListContainerMapper;
import com.schedulerates.setting.model.container.mapper.ContainerEntityToContainerMapper;
import com.schedulerates.setting.repository.ContainerRepository;
import com.schedulerates.setting.service.container.ContainerReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation named {@link ContainerReadServiceImpl} for reading
 * Containers.
 */
@Service
@RequiredArgsConstructor
public class ContainerReadServiceImpl implements ContainerReadService {

    private final ContainerRepository containerRepository;

    private final ContainerEntityToContainerMapper containerEntityToContainerMapper = ContainerEntityToContainerMapper
            .initialize();

    private final ListContainerEntityToListContainerMapper listContainerEntityToListContainerMapper = ListContainerEntityToListContainerMapper
            .initialize();

    /**
     * Retrieves a Container by its unique ID.
     *
     * @param ContainerId The ID of the Container to retrieve.
     * @return The Container object corresponding to the given ID.
     * @throws ContainerNotFoundException If no Container with the given ID exists.
     */
    @Override
    public Container getContainerById(String containerId) {

        final ContainerEntity containerEntityFromDB = containerRepository
                .findById(containerId)
                .orElseThrow(() -> new NotFoundException("With given containerID = " + containerId));

        return containerEntityToContainerMapper.map(containerEntityFromDB);
    }

    /**
     * Retrieves a list of all containers.
     *
     * @return A list of Container objects, each representing a container retrieved
     *         from the database.
     */

    @Override
    public List<Container> getAllContainers() {
        List<ContainerEntity> entities = containerRepository.findAll();
        return entities.stream()
                .map(containerEntityToContainerMapper::map)
                .toList();
    }

    /**
     * Retrieves a page of Containers based on the paging request criteria.
     *
     * @param ContainerPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of Containers that match the paging
     *         criteria.
     * @throws ContainerNotFoundException If no Containers are found based on the
     *                                    paging
     *                                    criteria.
     */
    @Override
    public CustomPage<Container> getContainers(ContainerPagingRequest containerPagingRequest) {
        final Page<ContainerEntity> containerEntityPage;
        if (containerPagingRequest.getSearch() != null && !containerPagingRequest.getSearch().isEmpty()) {
            containerEntityPage = containerRepository
                    .findBycontainerNameContainingIgnoreCase(containerPagingRequest.getSearch(),
                            containerPagingRequest.toPageable());
        } else {
            containerEntityPage = containerRepository.findAll(containerPagingRequest.toPageable());
        }

        final List<Container> containerDomainModels = listContainerEntityToListContainerMapper
                .toContainerList(containerEntityPage.getContent());

        return CustomPage.of(containerDomainModels, containerEntityPage);

    }

}