package com.schedulerates.setting.service.port;

import java.util.List;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.request.PortPagingRequest;

/**
 * Service interface named {@link PortReadService} for reading ports.
 */
public interface PortReadService {

    /**
     * Retrieves a port by its unique ID.
     *
     * @param portId The ID of the port to retrieve.
     * @return The Port object corresponding to the given ID.
     */
    Port getPortById(final String portId);

         /**
     * Retrieves all ports.
     *
     * @return A list of all ports.
     */
    List<Port> getAllPorts();

    /**
     * Retrieves a page of ports based on the paging request criteria.
     *
     * @param portPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of ports that match the paging criteria.
     */
    CustomPage<Port> getPorts(final PortPagingRequest portPagingRequest);

}
