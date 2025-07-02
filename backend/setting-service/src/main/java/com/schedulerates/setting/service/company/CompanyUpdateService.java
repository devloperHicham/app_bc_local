package com.schedulerates.setting.service.company;

import org.springframework.web.multipart.MultipartFile;

import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.request.CompanyUpdateRequest;

/**
 * Service interface named {@link CompanyUpdateService} for updating companies.
 */
public interface CompanyUpdateService {

    /**
     * Updates a company identified by its unique ID using the provided update request.
     *
     * @param companyId           The ID of the company to update.
     * @param companyUpdateRequest The request containing updated data for the company.
     * @return The updated Company object.
     */
    Company updateCompanyById(final String companyId, CompanyUpdateRequest companyUpdateRequest, MultipartFile logoFile);

}