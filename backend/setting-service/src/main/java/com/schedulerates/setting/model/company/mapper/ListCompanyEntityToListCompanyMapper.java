package com.schedulerates.setting.model.company.mapper;

import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListCompanyEntityToListCompanyMapper} for converting {@link List<CompanyEntity>} to {@link List<Company>} .
 */
@Mapper
public interface ListCompanyEntityToListCompanyMapper {

    CompanyEntityToCompanyMapper companyEntityToCompanyMapper = Mappers.getMapper(CompanyEntityToCompanyMapper.class);

    /**
     * Converts a list of CompanyEntity objects to a list of Company objects.
     *
     * @param companyEntities The list of CompanyEntity objects to convert.
     * @return List of Company objects containing mapped data.
     */
    default List<Company> toCompanyList(List<CompanyEntity> companyEntities) {

        if (companyEntities == null) {
            return null;
        }

        return companyEntities.stream()
                .map(companyEntityToCompanyMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListCompanyEntityToListCompanyMapper.
     *
     * @return Initialized ListCompanyEntityToListCompanyMapper instance.
     */
    static ListCompanyEntityToListCompanyMapper initialize() {
        return Mappers.getMapper(ListCompanyEntityToListCompanyMapper.class);
    }

}