package com.schedulerates.setting.service.gargo;

import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.request.GargoUpdateRequest;

/**
 * Service interface named {@link GargoUpdateService} for updating Gargos.
 */
public interface GargoUpdateService {

    /**
     * Updates a Gargo identified by its unique ID using the provided update request.
     *
     * @param gargoId           The ID of the Gargo to update.
     * @param gargoUpdateRequest The request containing updated data for the Gargo.
     * @return The updated Gargo object.
     */
    Gargo updateGargoById(final String gargoId, final GargoUpdateRequest gargoUpdateRequest);

}
