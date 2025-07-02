package com.schedulerates.setting.model.company.mapper;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.response.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Company>} to {@link CustomPagingResponse<CompanyResponse>}.
 */
@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    CompanyToCompanyResponseMapper companyToCompanyResponseMapper = Mappers.getMapper(CompanyToCompanyResponseMapper.class);

    /**
     * Converts a CustomPage<Company> object to CustomPagingResponse<CompanyResponse>.
     *
     * @param companyPage The CustomPage<Company> object to convert.
     * @return CustomPagingResponse<CompanyResponse> object containing mapped data.
     */
    default CustomPagingResponse<CompanyResponse> toPagingResponse(CustomPage<Company> companyPage) {

        if (companyPage == null) {
            return null;
        }

        return CustomPagingResponse.<CompanyResponse>builder()
                .content(toCompanyResponseList(companyPage.getContent()))
                .totalElementCount(companyPage.getTotalElementCount())
                .totalPageCount(companyPage.getTotalPageCount())
                .pageNumber(companyPage.getPageNumber())
                .pageSize(companyPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Company objects to a list of CompanyResponse objects.
     *
     * @param companies The list of Company objects to convert.
     * @return List of CompanyResponse objects containing mapped data.
     */
    default List<CompanyResponse> toCompanyResponseList(List<Company> companies) {

        if (companies == null) {
            return null;
        }

        return companies.stream()
                .map(companyToCompanyResponseMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of CustomPageToCustomPagingResponseMapper.
     *
     * @return Initialized CustomPageToCustomPagingResponseMapper instance.
     */
    static CustomPageToCustomPagingResponseMapper initialize() {
        return Mappers.getMapper(CustomPageToCustomPagingResponseMapper.class);
    }

}