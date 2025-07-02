package com.schedulerates.setting.service.gargo;

import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.request.GargoCreateRequest;

/**
 * Service interface named {@link FaqCreateService} for creating FAQs.
 */
public interface GargoCreateService {

    /**
     * Creates a new GARGO based on the provided GARGO creation request.
     *
     * @param gargoCreateRequest The request containing data to create the GARGO.
     * @return The created GARGO object.
     */
    Gargo createGargo(final GargoCreateRequest gargoCreateRequest);

}
