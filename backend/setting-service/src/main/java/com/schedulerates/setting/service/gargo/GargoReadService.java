package com.schedulerates.setting.service.gargo;

import java.util.List;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.request.GargoPagingRequest;

/**
 * Service interface named {@link GargoReadService} for reading Gargos.
 */
public interface GargoReadService {

    /**
     * Retrieves a Gargo by its unique ID.
     *
     * @param gargoId The ID of the Gargo to retrieve.
     * @return The Gargo object corresponding to the given ID.
     */
    Gargo getGargoById(final String gargoId);

         /**
     * Retrieves all gargos.
     *
     * @return A list of all gargos.
     */
    List<Gargo> getAllGargos();

    /**
     * Retrieves a page of Gargos based on the paging request criteria.
     *
     * @param gargoPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of Gargos that match the paging criteria.
     */
    CustomPage<Gargo> getGargos(final GargoPagingRequest gargoPagingRequest);

}
