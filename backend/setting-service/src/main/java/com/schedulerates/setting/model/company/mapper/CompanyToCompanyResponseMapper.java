package com.schedulerates.setting.model.company.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.response.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link CompanyToCompanyResponseMapper} for converting {@link Company} to {@link CompanyResponse}.
 */
@Mapper
public interface CompanyToCompanyResponseMapper extends BaseMapper<Company, CompanyResponse> {

    /**
     * Maps Company to CompanyResponse.
     *
     * @param source The Company object to map.
     * @return CompanyResponse object containing mapped data.
     */
    @Override
    CompanyResponse map(Company source);

    /**
     * Initializes and returns an instance of CompanyToCompanyResponseMapper.
     *
     * @return Initialized CompanyToCompanyResponseMapper instance.
     */
    static CompanyToCompanyResponseMapper initialize() {
        return Mappers.getMapper(CompanyToCompanyResponseMapper.class);
    }

}