package com.schedulerates.setting.service.container;

import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.request.ContainerUpdateRequest;

/**
 * Service interface named {@link ContainerUpdateService} for updating Containers.
 */
public interface ContainerUpdateService {

    /**
     * Updates a Container identified by its unique ID using the provided update request.
     *
     * @param ContainerId           The ID of the Container to update.
     * @param ContainerUpdateRequest The request containing updated data for the Container.
     * @return The updated Container object.
     */
    Container updateContainerById(final String containerId, final ContainerUpdateRequest containerUpdateRequest);

}