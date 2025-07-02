package com.schedulerates.setting.model.company.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.company.dto.request.CompanyUpdateRequest;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

/**
 * Mapper interface named {@link CompanyUpdateRequestToCompanyEntityMapper} for
 * updating {@link CompanyEntity} using {@link CompanyUpdateRequest}.
 */
@Mapper
public interface CompanyUpdateRequestToCompanyEntityMapper extends BaseMapper<CompanyUpdateRequest, CompanyEntity> {
    /**
     * Maps MultipartFile to String (e.g., original filename).
     *
     * @param file MultipartFile to map.
     * @return String representation (e.g., original filename) or null if file is
     *         null.
     */
    default String map(MultipartFile file) {
        return (file != null) ? file.getOriginalFilename() : null;
    }

    /**
     * Maps CompanyUpdateRequest to CompanyEntity for saving purposes.
     *
     * @param CompanyUpdateRequest The CompanyUpdateRequest object to map.
     * @return CompanyEntity object containing mapped data.
     */
    @Named("mapForUpdating")
    default CompanyEntity mapForUpdating(CompanyUpdateRequest request) {
        String logoPath = null;
        if (request.getCompanyLogo() != null && !request.getCompanyLogo().isEmpty()) {
            // In a real app, you'd upload and get the saved file path here
            logoPath = request.getCompanyLogo().getOriginalFilename();
        }

        return CompanyEntity.builder()
                .companyName(request.getCompanyName())
                .companyLogo(logoPath)
                .obs(request.getObs())
                .build();
    }

    /**
     * Initializes and returns an instance of
     * CompanyUpdateRequestToCompanyEntityMapper.
     *
     * @return Initialized CompanyUpdateRequestToCompanyEntityMapper instance.
     */
    static CompanyUpdateRequestToCompanyEntityMapper initialize() {
        return Mappers.getMapper(CompanyUpdateRequestToCompanyEntityMapper.class);
    }
}