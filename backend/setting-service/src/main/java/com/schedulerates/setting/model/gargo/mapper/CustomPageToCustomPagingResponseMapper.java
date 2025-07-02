package com.schedulerates.setting.model.gargo.mapper;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.response.GargoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Faq>} to {@link CustomPagingResponse<FaqResponse>}.
 */

@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    GargoToGargoResponseMapper gargoToGargoResponseMapper = Mappers.getMapper(GargoToGargoResponseMapper.class);

    /**
     * Converts a CustomPage<Gargo> object to CustomPagingResponse<GargoResponse>.
     *
     * @param gargoPage The CustomPage<Gargo> object to convert.
     * @return CustomPagingResponse<GargoResponse> object containing mapped data.
     */
    default CustomPagingResponse<GargoResponse> toPagingResponse(CustomPage<Gargo> gargoPage) {

        if (gargoPage == null) {
            return null;
        }

        return CustomPagingResponse.<GargoResponse>builder()
                .content(toGargoResponseList(gargoPage.getContent()))
                .totalElementCount(gargoPage.getTotalElementCount())
                .totalPageCount(gargoPage.getTotalPageCount())
                .pageNumber(gargoPage.getPageNumber())
                .pageSize(gargoPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Gargo objects to a list of GargoResponse objects.
     *
     * @param gargos The list of Gargo objects to convert.
     * @return List of GargoResponse objects containing mapped data.
     */
    default List<GargoResponse> toGargoResponseList(List<Gargo> gargos) {

        if (gargos == null) {
            return null;
        }

        return gargos.stream()
                .map(gargoToGargoResponseMapper::map)
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
