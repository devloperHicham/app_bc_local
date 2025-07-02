package com.schedulerates.setting.service.container;

import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.request.ContainerCreateRequest;

/**
 * Service interface named {@link ContainerCreateService} for creating Containers.
 */
public interface ContainerCreateService {

    /**
     * Creates a new Container based on the provided Container creation request.
     *
     * @param containerCreateRequest The request containing data to create the container.
     * @return The created Container object.
     */
    Container createContainer(final ContainerCreateRequest containerCreateRequest);

}