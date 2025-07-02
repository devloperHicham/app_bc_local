package com.schedulerates.setting.service.company;

import org.springframework.web.multipart.MultipartFile;

import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.request.CompanyCreateRequest;

/**
 * Service interface named {@link CompanyCreateService} for creating companies.
 */
public interface CompanyCreateService {

    /**
     * Creates a new company based on the provided company creation request.
     *
     * @param companyCreateRequest The request containing data to create the company.
     * @return The created Company object.
     */
    Company createCompany(CompanyCreateRequest companyCreateRequest, MultipartFile logoFile);

}