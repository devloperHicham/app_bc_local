package com.schedulerates.setting.service.port;

import org.springframework.web.multipart.MultipartFile;

import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.request.PortCreateRequest;

/**
 * Service interface named {@link PortCreateService} for creating companies.
 */
public interface PortCreateService {

    /**
     * Creates a new port based on the provided port creation request.
     *
     * @param portCreateRequest The request containing data to create the port.
     * @return The created Port object.
     */
    Port createPort(PortCreateRequest portCreateRequest, MultipartFile logoFile);

}
