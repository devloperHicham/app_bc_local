package com.schedulerates.setting.service.container;

/**
 * Service interface named {@link ContainerDeleteService} for deleting Containers.
 */
public interface ContainerDeleteService {

    /**
     * Deletes a Container identified by its unique ID.
     *
     * @param containerId The ID of the container to delete.
     */
    void deleteContainerById(final String containerId);

}