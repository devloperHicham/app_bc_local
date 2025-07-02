package com.schedulerates.setting.service.gargo.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.request.GargoUpdateRequest;
import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import com.schedulerates.setting.model.gargo.mapper.GargoEntityToGargoMapper;
import com.schedulerates.setting.model.gargo.mapper.GargoUpdateRequestToGargoEntityMapper;
import com.schedulerates.setting.repository.GargoRepository;
import com.schedulerates.setting.service.gargo.GargoUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link GargoUpdateServiceImpl} for updating Gargos.
 */
@Service
@RequiredArgsConstructor
public class GargoUpdateServiceImpl implements GargoUpdateService {

    private final GargoRepository gargoRepository;

    private final GargoUpdateRequestToGargoEntityMapper gargoUpdateRequestToGargoEntityMapper =
            GargoUpdateRequestToGargoEntityMapper.initialize();

    private final GargoEntityToGargoMapper gargoEntityToGargoMapper =
            GargoEntityToGargoMapper.initialize();

    /**
     * Updates a Gargo identified by its unique ID using the provided update request.
     *
     * @param gargoId           The ID of the Gargo to update.
     * @param gargoUpdateRequest The request containing updated data for the Gargo.
     * @return The updated Gargo object.
     * @throws GargoNotFoundException If no Gargo with the given ID exists.
     * @throws GargoAlreadyExistException If another Gargo with the updated name already exists.
     */
    @Override
    public Gargo updateGargoById(String gargoId, GargoUpdateRequest gargoUpdateRequest) {

        final GargoEntity gargoEntityToBeUpdate = gargoRepository
                .findById(gargoId)
                .orElseThrow(() -> new NotFoundException("With given gargoID = " + gargoId));

        gargoUpdateRequestToGargoEntityMapper.mapForUpdating(gargoEntityToBeUpdate, gargoUpdateRequest);

        GargoEntity updatedGargoEntity = gargoRepository.save(gargoEntityToBeUpdate);

        return gargoEntityToGargoMapper.map(updatedGargoEntity);

    }
}
