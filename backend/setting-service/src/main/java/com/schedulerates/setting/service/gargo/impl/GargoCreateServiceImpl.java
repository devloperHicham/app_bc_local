package com.schedulerates.setting.service.gargo.impl;

import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.request.GargoCreateRequest;
import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import com.schedulerates.setting.model.gargo.mapper.GargoCreateRequestToGargoEntityMapper;
import com.schedulerates.setting.model.gargo.mapper.GargoEntityToGargoMapper;
import com.schedulerates.setting.repository.GargoRepository;
import com.schedulerates.setting.service.gargo.GargoCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link GargoCreateServiceImpl} for creating Gargos.
 */
@Service
@RequiredArgsConstructor
public class GargoCreateServiceImpl implements GargoCreateService {

    private final GargoRepository gargoRepository;

    private final GargoCreateRequestToGargoEntityMapper gargoCreateRequestToGargoEntityMapper =
            GargoCreateRequestToGargoEntityMapper.initialize();

    private final GargoEntityToGargoMapper gargoEntityToGargoMapper = GargoEntityToGargoMapper.initialize();

    /**
     * Creates a new Gargo based on the provided Gargo creation request.
     *
     * @param gargoCreateRequest The request containing data to create the Gargo.
     * @return The created Gargo object.
     * @throws GargoAlreadyExistException If a Gargo with the same question already exists.
     */
    @Override
    public Gargo createGargo(GargoCreateRequest gargoCreateRequest) {

        final GargoEntity gargoEntityToBeSave = gargoCreateRequestToGargoEntityMapper.mapForSaving(gargoCreateRequest);

        GargoEntity savedGargoEntity = gargoRepository.save(gargoEntityToBeSave);

        return gargoEntityToGargoMapper.map(savedGargoEntity);

    }

}
