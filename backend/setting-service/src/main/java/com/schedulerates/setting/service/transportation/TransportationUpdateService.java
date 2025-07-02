package com.schedulerates.setting.service.transportation;

import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.request.TransportationUpdateRequest;

/**
 * Service interface named {@link TransportationUpdateService} for updating Transportations.
 */
public interface TransportationUpdateService {

    /**
     * Updates a Transportation identified by its unique ID using the provided update request.
     *
     * @param transportationId           The ID of the Transportation to update.
     * @param transportationUpdateRequest The request containing updated data for the Transportation.
     * @return The updated Transportation object.
     */
    Transportation updateTransportationById(final String transportationId, final TransportationUpdateRequest transportationUpdateRequest);

}
