package com.schedulerates.setting.service.company;

/**
 * Service interface named {@link CompanyDeleteService} for deleting companies.
 */
public interface CompanyDeleteService {

    /**
     * Deletes a company identified by its unique ID.
     *
     * @param companyId The ID of the company to delete.
     */
    void deleteCompanyById(final String companyId);

}