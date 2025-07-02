package com.schedulerates.setting.service.gargo;

/**
 * Service interface named {@link GargoDeleteService} for deleting Gargos.
 */
public interface GargoDeleteService {

    /**
     * Deletes a Gargo identified by its unique ID.
     *
     * @param gargoId The ID of the Gargo to delete.
     */
    void deleteGargoById(final String gargoId);

}
