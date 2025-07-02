package com.schedulerates.setting.service.company.impl;

import com.schedulerates.setting.exception.AlreadyExistException;
import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.request.CompanyUpdateRequest;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import com.schedulerates.setting.model.company.mapper.CompanyEntityToCompanyMapper;
import com.schedulerates.setting.repository.CompanyRepository;
import com.schedulerates.setting.service.company.CompanyUpdateService;
import com.schedulerates.setting.storage.StorageImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanyUpdateServiceImpl implements CompanyUpdateService {

    private final CompanyRepository companyRepository;
    private final StorageImageService storageService;

    private final CompanyEntityToCompanyMapper companyEntityToCompanyMapper = CompanyEntityToCompanyMapper.initialize();

    /**
     * Creates a new company with logo upload support.
     *
     * @param updateRequest The company creation request
     * @param logoFile      Optional logo file (can be null)
     * @return The created Company object
     * @throws AlreadyExistException If company name exists
     */
    @Override
    public Company updateCompanyById(String companyId,
            CompanyUpdateRequest updateRequest,
            MultipartFile newLogoFile) {

        // 1. Fetch existing company
        CompanyEntity entity = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        // 2. Validate at least one field is being updated
        if (updateRequest.getCompanyName() == null && (newLogoFile == null || newLogoFile.isEmpty())) {
            throw new IllegalArgumentException("At least one field (name or logo) must be updated");
        }

        // 3. Handle company name update (if provided)
        if (updateRequest.getCompanyName() != null) {
            validateCompanyNameUniqueness(updateRequest.getCompanyName(), entity.getCompanyName());
            entity.setCompanyName(updateRequest.getCompanyName());
        }

        // 4. Handle logo update (if provided)
        if (newLogoFile != null && !newLogoFile.isEmpty()) {
            updateCompanyLogo(entity, newLogoFile);
        }

        // 5. Update observation if provided
        if (updateRequest.getObs() != null) {
            entity.setObs(updateRequest.getObs());
        }

        // 6. Save the updated entity
        CompanyEntity savedEntity = companyRepository.save(entity);

        return companyEntityToCompanyMapper.map(savedEntity);
    }

    private void updateCompanyLogo(CompanyEntity entity, MultipartFile newLogoFile) {
        try {
            String  storedFilename = storageService.store(newLogoFile);
            entity.setCompanyLogo(storedFilename);
        } catch (Exception e) {
            throw new NotFoundException("Failed to update company logo: " + e.getMessage());
        }
    }

    private void validateCompanyNameUniqueness(String newName, String currentName) {
        if (!newName.equals(currentName) && companyRepository.existsCompanyEntityByCompanyName(newName)) {
            throw new AlreadyExistException("Company name already exists");
        }
    }
}