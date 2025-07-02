package com.schedulerates.setting.service.port.impl;

import com.schedulerates.setting.client.ComparisonServiceClient;
import com.schedulerates.setting.client.ScheduleServiceClient;
import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.port.entity.PortEntity;
import com.schedulerates.setting.repository.PortRepository;
import com.schedulerates.setting.service.port.PortDeleteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link PortDeleteServiceImpl} for deleting companies.
 */
@Service
@RequiredArgsConstructor
public class PortDeleteServiceImpl implements PortDeleteService {

    private final PortRepository portRepository;
    private final ScheduleServiceClient scheduleServiceClient;
    private final ComparisonServiceClient comparisonServiceClient;
    private final HttpServletRequest httpServletRequest; // Inject the current request

    /**
     * Deletes a port identified by its unique ID.
     *
     * @param portId The ID of the port to delete.
     * @throws NotFoundException If no port with the given ID exists.
     */
    @Override
    public void deletePortById(String portId) {
        String authHeader = httpServletRequest.getHeader("Authorization");

        final PortEntity portEntityToBeDelete = portRepository
                .findById(portId)
                .orElseThrow(() -> new NotFoundException("With given portID = " + portId));

                // Check if in use
        boolean isUsedSchedule = scheduleServiceClient.existsByPortById(portId, authHeader);
        boolean isUsedComparison = comparisonServiceClient.existsByPortId(portId, authHeader);
        if (isUsedSchedule || isUsedComparison) {
            throw new NotFoundException("port is in use");
        }
        portRepository.delete(portEntityToBeDelete);

    }

}
