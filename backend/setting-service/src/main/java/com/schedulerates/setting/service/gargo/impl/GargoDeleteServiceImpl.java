package com.schedulerates.setting.service.gargo.impl;

import com.schedulerates.setting.client.ComparisonServiceClient;
import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.gargo.entity.GargoEntity;
import com.schedulerates.setting.repository.GargoRepository;
import com.schedulerates.setting.service.gargo.GargoDeleteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link GargoDeleteServiceImpl} for deleting FAQs.
 */
@Service
@RequiredArgsConstructor
public class GargoDeleteServiceImpl implements GargoDeleteService {

    private final GargoRepository gargoRepository;
    private final ComparisonServiceClient comparisonServiceClient;
    private final HttpServletRequest httpServletRequest; // Inject the current request

    /**
     * Deletes a Gargo identified by its unique ID.
     *
     * @param gargoId The ID of the Gargo to delete.
     * @throws GargoNotFoundException If no Gargo with the given ID exists.
     */
    @Override
    public void deleteGargoById(String gargoId) {
        String authHeader = httpServletRequest.getHeader("Authorization");

        final GargoEntity gargoEntityToBeDelete = gargoRepository
                .findById(gargoId)
                .orElseThrow(() -> new NotFoundException("With given gargoID = " + gargoId));

                // Check if in use
        boolean isUsedComparison = comparisonServiceClient.existsByGargoById(gargoId, authHeader);
        if (isUsedComparison) {
            throw new NotFoundException("gargo is in use");
        }
        gargoRepository.delete(gargoEntityToBeDelete);

    }

}
