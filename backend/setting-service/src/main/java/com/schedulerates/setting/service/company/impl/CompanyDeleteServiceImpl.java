package com.schedulerates.setting.service.company.impl;

import com.schedulerates.setting.client.ComparisonServiceClient;
import com.schedulerates.setting.client.ScheduleServiceClient;
import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import com.schedulerates.setting.repository.CompanyRepository;
import com.schedulerates.setting.service.company.CompanyDeleteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link CompanyDeleteServiceImpl} for deleting
 * companies.
 */
@Service
@RequiredArgsConstructor
public class CompanyDeleteServiceImpl implements CompanyDeleteService {

    private final CompanyRepository companyRepository;
    private final ScheduleServiceClient scheduleServiceClient;
    private final ComparisonServiceClient comparisonServiceClient;
    private final HttpServletRequest httpServletRequest; // Inject the current request

    /**
     * Deletes a company identified by its unique ID.
     *
     * @param companyId The ID of the company to delete.
     * @throws NotFoundException If no company with the given ID exists.
     */
    @Override
    public void deleteCompanyById(String companyId) {
        String authHeader = httpServletRequest.getHeader("Authorization");

        final CompanyEntity companyEntityToBeDelete = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new NotFoundException("With given companyID = " + companyId));

        // Check if in use
        boolean isUsedSchedule = scheduleServiceClient.existsByCompanyById(companyId, authHeader);
        boolean isUsedComparison = comparisonServiceClient.existsByCompanyById(companyId, authHeader);
        if (isUsedSchedule || isUsedComparison) {
            throw new NotFoundException("Company is in use");
        }
        companyRepository.delete(companyEntityToBeDelete);

    }

}