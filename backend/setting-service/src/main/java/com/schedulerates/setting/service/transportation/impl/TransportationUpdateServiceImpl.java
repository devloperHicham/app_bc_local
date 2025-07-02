package com.schedulerates.setting.service.transportation.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.request.TransportationUpdateRequest;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import com.schedulerates.setting.model.transportation.mapper.TransportationEntityToTransportationMapper;
import com.schedulerates.setting.model.transportation.mapper.TransportationUpdateRequestToTransportationEntityMapper;
import com.schedulerates.setting.repository.TransportationRepository;
import com.schedulerates.setting.service.transportation.TransportationUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link TransportationUpdateServiceImpl} for updating Transportations.
 */
@Service
@RequiredArgsConstructor
public class TransportationUpdateServiceImpl implements TransportationUpdateService {

    private final TransportationRepository transportationRepository;

    private final TransportationUpdateRequestToTransportationEntityMapper transportationUpdateRequestToTransportationEntityMapper =
            TransportationUpdateRequestToTransportationEntityMapper.initialize();

    private final TransportationEntityToTransportationMapper transportationEntityToTransportationMapper =
            TransportationEntityToTransportationMapper.initialize();

    /**
     * Updates a Transportation identified by its unique ID using the provided update request.
     *
     * @param transportationId           The ID of the Transportation to update.
     * @param transportationUpdateRequest The request containing updated data for the Transportation.
     * @return The updated Transportation object.
     * @throws TransportationNotFoundException If no Transportation with the given ID exists.
     * @throws TransportationAlreadyExistException If another Transportation with the updated name already exists.
     */
    @Override
    public Transportation updateTransportationById(String transportationId, TransportationUpdateRequest transportationUpdateRequest) {

        final TransportationEntity transportationEntityToBeUpdate = transportationRepository
                .findById(transportationId)
                .orElseThrow(() -> new NotFoundException("With given transportationID = " + transportationId));

        transportationUpdateRequestToTransportationEntityMapper.mapForUpdating(transportationEntityToBeUpdate, transportationUpdateRequest);

        TransportationEntity updatedTransportationEntity = transportationRepository.save(transportationEntityToBeUpdate);

        return transportationEntityToTransportationMapper.map(updatedTransportationEntity);

    }
}
