package com.schedulerates.setting.service.gargo.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.request.GargoPagingRequest;
import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import com.schedulerates.setting.model.gargo.mapper.ListGargoEntityToListGargoMapper;
import com.schedulerates.setting.model.gargo.mapper.GargoEntityToGargoMapper;
import com.schedulerates.setting.repository.GargoRepository;
import com.schedulerates.setting.service.gargo.GargoReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation named {@link GargoReadServiceImpl} for reading Gargos.
 */
@Service
@RequiredArgsConstructor
public class GargoReadServiceImpl implements GargoReadService {

    private final GargoRepository gargoRepository;

    private final GargoEntityToGargoMapper gargoEntityToGargoMapper = GargoEntityToGargoMapper.initialize();

    private final ListGargoEntityToListGargoMapper listGargoEntityToListGargoMapper = ListGargoEntityToListGargoMapper.initialize();

    /**
     * Retrieves a Gargo by its unique ID.
     *
     * @param gargoId The ID of the Gargo to retrieve.
     * @return The Gargo object corresponding to the given ID.
     * @throws GargoNotFoundException If no Gargo with the given ID exists.
     */
    @Override
    public Gargo getGargoById(String gargoId) {

        final GargoEntity gargoEntityFromDB = gargoRepository
                .findById(gargoId)
                .orElseThrow(() -> new NotFoundException("With given gargoID = " + gargoId));

        return gargoEntityToGargoMapper.map(gargoEntityFromDB);
    }

    /**
     * Retrieves all Gargos.
     *
     * @return A list of all Gargos.
     */
    @Override
    public List<Gargo> getAllGargos() {
        List<GargoEntity> entities = gargoRepository.findAll();
        return entities.stream()
                .map(gargoEntityToGargoMapper::map)
                .toList();
    }

    /**
     * Retrieves a page of Gargos based on the paging request criteria.
     *
     * @param gargoPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of Gargos that match the paging
     *         criteria.
     * @throws GargoNotFoundException If no Gargos are found based on the paging
     *                              criteria.
     */
    @Override
    public CustomPage<Gargo> getGargos(GargoPagingRequest gargoPagingRequest) {
        final Page<GargoEntity> gargoEntityPage;
        if (gargoPagingRequest.getSearch() != null && !gargoPagingRequest.getSearch().isEmpty()) {
            gargoEntityPage = gargoRepository
                    .findByGargoNameContainingIgnoreCase(gargoPagingRequest.getSearch(), gargoPagingRequest.toPageable());
        } else {
            gargoEntityPage = gargoRepository.findAll(gargoPagingRequest.toPageable());
        }
        
        final List<Gargo> gargoDomainModels = listGargoEntityToListGargoMapper
                .toGargoList(gargoEntityPage.getContent());

        return CustomPage.of(gargoDomainModels, gargoEntityPage);

    }

}
