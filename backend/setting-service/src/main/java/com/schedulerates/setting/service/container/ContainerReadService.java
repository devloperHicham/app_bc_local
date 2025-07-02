package com.schedulerates.setting.service.container;

import java.util.List;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.request.ContainerPagingRequest;

/**
 * Service interface named {@link ContainerReadService} for reading Containers.
 */
public interface ContainerReadService {

    /**
     * Retrieves a Container by its unique ID.
     *
     * @param containerId The ID of the container to retrieve.
     * @return The container object corresponding to the given ID.
     */
    Container getContainerById(final String containerId);

         /**
     * Retrieves all containers.
     *
     * @return A list of all containers.
     */
    List<Container> getAllContainers();
    /**
     * Retrieves a page of containers based on the paging request criteria.
     *
     * @param containerPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of containers that match the paging criteria.
     */
    CustomPage<Container> getContainers(final ContainerPagingRequest containerPagingRequest);

}