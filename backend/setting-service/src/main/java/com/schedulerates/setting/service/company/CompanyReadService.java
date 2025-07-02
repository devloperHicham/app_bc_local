package com.schedulerates.setting.service.company;

import java.util.List;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.request.CompanyPagingRequest;

/**
 * Service interface named {@link CompanyReadService} for reading companies.
 */
public interface CompanyReadService {

    /**
     * Retrieves a company by its unique ID.
     *
     * @param companyId The ID of the company to retrieve.
     * @return The Company object corresponding to the given ID.
     */
    Company getCompanyById(final String companyId);

     /**
     * Retrieves all companies.
     *
     * @return A list of all companies.
     */
    List<Company> getAllCompanies();

    /**
     * Retrieves a page of companies based on the paging request criteria.
     *
     * @param companyPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of companies that match the paging criteria.
     */
    CustomPage<Company> getCompanies(final CompanyPagingRequest companyPagingRequest);

}