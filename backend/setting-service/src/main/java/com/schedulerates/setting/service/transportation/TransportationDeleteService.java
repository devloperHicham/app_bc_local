package com.schedulerates.setting.service.transportation;

/**
 * Service interface named {@link TransportationDeleteService} for deleting Transportations.
 */
public interface TransportationDeleteService {

    /**
     * Deletes a Transportation identified by its unique ID.
     *
     * @param transportationId The ID of the Transportation to delete.
     */
    void deleteTransportationById(final String transportationId);

}
