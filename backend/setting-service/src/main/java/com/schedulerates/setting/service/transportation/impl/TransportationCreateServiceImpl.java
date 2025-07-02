package com.schedulerates.setting.service.transportation.impl;

import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.request.TransportationCreateRequest;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import com.schedulerates.setting.model.transportation.mapper.TransportationCreateRequestToTransportationEntityMapper;
import com.schedulerates.setting.model.transportation.mapper.TransportationEntityToTransportationMapper;
import com.schedulerates.setting.repository.TransportationRepository;
import com.schedulerates.setting.service.transportation.TransportationCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link TransportationCreateServiceImpl} for creating Transportations.
 */
@Service
@RequiredArgsConstructor
public class TransportationCreateServiceImpl implements TransportationCreateService {

    private final TransportationRepository transportationRepository;

    private final TransportationCreateRequestToTransportationEntityMapper transportationCreateRequestToTransportationEntityMapper =
            TransportationCreateRequestToTransportationEntityMapper.initialize();

    private final TransportationEntityToTransportationMapper transportationEntityToTransportationMapper =
            TransportationEntityToTransportationMapper.initialize();

    /**
     * Creates a new Transportation based on the provided Transportation creation request.
     *
     * @param transportationCreateRequest The request containing data to create the Transportation.
     * @return The created Transportation object.
     * @throws TransportationAlreadyExistException If a Transportation with the same name already exists.
     */
    @Override
    public Transportation createTransportation(TransportationCreateRequest transportationCreateRequest) {

        final TransportationEntity transportationEntityToBeSave = 
                transportationCreateRequestToTransportationEntityMapper.mapForSaving(transportationCreateRequest);

        TransportationEntity savedTransportationEntity = transportationRepository.save(transportationEntityToBeSave);

        return transportationEntityToTransportationMapper.map(savedTransportationEntity);

    }

}
