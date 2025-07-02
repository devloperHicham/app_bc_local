package com.schedulerates.setting.service.transportation;

import java.util.List;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.request.TransportationPagingRequest;

/**
 * Service interface named {@link TransportationReadService} for reading Transportations.
 */
public interface TransportationReadService {

    /**
     * Retrieves a Transportation by its unique ID.
     *
     * @param transportationId The ID of the Transportation to retrieve.
     * @return The Transportation object corresponding to the given ID.
     */
    Transportation getTransportationById(final String transportationId);

         /**
     * Retrieves all Transportations.
     *
     * @return A list of all Transportations.
     */
    List<Transportation> getAllTransportations();

    /**
     * Retrieves a page of Transportations based on the paging request criteria.
     *
     * @param transportationPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of Transportations that match the paging criteria.
     */
    CustomPage<Transportation> getTransportations(final TransportationPagingRequest transportationPagingRequest);

}
