package com.schedulerates.setting.service.port;

/**
 * Service interface named {@link PortDeleteService} for deleting companies.
 */
public interface PortDeleteService {

    /**
     * Deletes a port identified by its unique ID.
     *
     * @param portId The ID of the port to delete.
     */
    void deletePortById(final String portId);

}
