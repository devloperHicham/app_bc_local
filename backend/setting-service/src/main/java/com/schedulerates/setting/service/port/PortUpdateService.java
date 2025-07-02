package com.schedulerates.setting.service.port;

import org.springframework.web.multipart.MultipartFile;

import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.request.PortUpdateRequest;

/**
 * Service interface named {@link PortUpdateService} for updating ports.
 */
public interface PortUpdateService {

    /**
     * Updates a port identified by its unique ID using the provided update request.
     *
     * @param portId           The ID of the port to update.
     * @param portUpdateRequest The request containing updated data for the port.
     * @return The updated Port object.
     */
    Port updatePortById(final String portId, PortUpdateRequest portUpdateRequest, MultipartFile logoFile);

}
