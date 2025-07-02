package com.schedulerates.setting.service.company.impl;

import com.schedulerates.setting.exception.AlreadyExistException;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.request.CompanyCreateRequest;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import com.schedulerates.setting.model.company.mapper.CompanyEntityToCompanyMapper;
import com.schedulerates.setting.repository.CompanyRepository;
import com.schedulerates.setting.service.company.CompanyCreateService;
import com.schedulerates.setting.storage.StorageImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service implementation for creating companies with logo upload support.
 */
@Service
@RequiredArgsConstructor
public class CompanyCreateServiceImpl implements CompanyCreateService {

    private final CompanyRepository companyRepository;
    private final StorageImageService storageService;

    private final CompanyEntityToCompanyMapper companyEntityToCompanyMapper = CompanyEntityToCompanyMapper.initialize();

    /**
     * Creates a new company with logo upload support.
     *
     * @param companyCreateRequest The company creation request
     * @param logoFile             Optional logo file (can be null)
     * @return The created Company object
     * @throws AlreadyExistException If company name exists
     */
    @Override
    public Company createCompany(CompanyCreateRequest companyCreateRequest, MultipartFile logoFile) {
        checkUniquenessCompanyName(companyCreateRequest.getCompanyName());

        String storedFilename = null;
        if (logoFile != null && !logoFile.isEmpty()) {
            storedFilename = storageService.store(logoFile);
        }

        // Instead of setting it back on companyCreateRequest, pass filename to mapper
        // or create entity here
        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyName(companyCreateRequest.getCompanyName())
                .companyLogo(storedFilename) // filename string, not MultipartFile
                .obs(companyCreateRequest.getObs())
                .build();

        CompanyEntity savedEntity = companyRepository.save(companyEntity);

        return companyEntityToCompanyMapper.map(savedEntity);
    }

    /**
     * Checks company name uniqueness
     */
    private void checkUniquenessCompanyName(String companyName) {
        if (companyRepository.existsCompanyEntityByCompanyName(companyName)) {
            throw new AlreadyExistException("Company with name '" + companyName + "' already exists");
        }
    }
}