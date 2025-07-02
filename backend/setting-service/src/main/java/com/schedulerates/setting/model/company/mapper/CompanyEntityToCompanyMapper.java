package com.schedulerates.setting.model.company.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link CompanyEntityToCompanyMapper} for converting {@link CompanyEntity} to {@link Company}.
 */
@Mapper
public interface CompanyEntityToCompanyMapper extends BaseMapper<CompanyEntity, Company> {

    /**
     * Maps CompanyEntity to Company.
     *
     * @param source The CompanyEntity object to map.
     * @return Company object containing mapped data.
     */
    @Override
    Company map(CompanyEntity source);

    /**
     * Initializes and returns an instance of CompanyEntityToCompanyMapper.
     *
     * @return Initialized CompanyEntityToCompanyMapper instance.
     */
    static CompanyEntityToCompanyMapper initialize() {
        return Mappers.getMapper(CompanyEntityToCompanyMapper.class);
    }

}