package com.schedulerates.setting.model.company.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.company.dto.request.CompanyCreateRequest;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

/**
 * Mapper interface named {@link CompanyCreateRequestToCompanyEntityMapper} for
 * converting {@link CompanyCreateRequest} to {@link CompanyEntity}.
 */

@Mapper
public interface CompanyCreateRequestToCompanyEntityMapper extends BaseMapper<CompanyCreateRequest, CompanyEntity> {

    /**
     * Maps MultipartFile to String (e.g., original filename).
     *
     * @param file MultipartFile to map.
     * @return String representation (e.g., original filename) or null if file is null.
     */
    default String map(MultipartFile file) {
        return (file != null) ? file.getOriginalFilename() : null;
    }

    /**
     * Maps CompanyCreateRequest to CompanyEntity for saving purposes.
     *
     * @param companyCreateRequest The CompanyCreateRequest object to map.
     * @return CompanyEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default CompanyEntity mapForSaving(CompanyCreateRequest request) {
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
     * CompanyCreateRequestToCompanyEntityMapper.
     *
     * @return Initialized CompanyCreateRequestToCompanyEntityMapper instance.
     */
    static CompanyCreateRequestToCompanyEntityMapper initialize() {
        return Mappers.getMapper(CompanyCreateRequestToCompanyEntityMapper.class);
    }

}