package com.schedulerates.setting.service.transportation.impl;

import com.schedulerates.setting.client.ComparisonServiceClient;
import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import com.schedulerates.setting.repository.TransportationRepository;
import com.schedulerates.setting.service.transportation.TransportationDeleteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link TransportationDeleteServiceImpl} for
 * deleting Transportations.
 */
@Service
@RequiredArgsConstructor
public class TransportationDeleteServiceImpl implements TransportationDeleteService {

    private final TransportationRepository transportationRepository;
    private final ComparisonServiceClient comparisonServiceClient;
    private final HttpServletRequest httpServletRequest; // Inject the current request

    /**
     * Deletes a Transportation identified by its unique ID.
     *
     * @param transportationId The ID of the Transportation to delete.
     * @throws TransportationNotFoundException If no Transportation with the given
     *                                         ID exists.
     */
    @Override
    public void deleteTransportationById(String transportationId) {
        String authHeader = httpServletRequest.getHeader("Authorization");

        final TransportationEntity transportationEntityToBeDelete = transportationRepository
                .findById(transportationId)
                .orElseThrow(() -> new NotFoundException("With given transportationId = " + transportationId));

        // Check if in use
        boolean isUsedComparison = comparisonServiceClient.existsByTransportationById(transportationId, authHeader);
        if (isUsedComparison) {
            throw new NotFoundException("transportationId is in use");
        }
        transportationRepository.delete(transportationEntityToBeDelete);

    }

}
