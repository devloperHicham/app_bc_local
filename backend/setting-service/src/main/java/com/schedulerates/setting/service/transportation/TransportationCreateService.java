package com.schedulerates.setting.service.transportation;

import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.request.TransportationCreateRequest;

/**
 * Service interface named {@link TransportationCreateService} for creating Transportations.
 */
public interface TransportationCreateService {

    /**
     * Creates a new Transportation based on the provided Transportation creation request.
     *
     * @param transportationCreateRequest The request containing data to create the Transportation.
     * @return The created Transportation object.
     */
    Transportation createTransportation(final TransportationCreateRequest transportationCreateRequest);

}
