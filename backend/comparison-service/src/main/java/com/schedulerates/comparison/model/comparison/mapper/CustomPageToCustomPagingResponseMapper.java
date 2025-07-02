package com.schedulerates.comparison.model.comparison.mapper;

import com.schedulerates.comparison.model.common.CustomPage;
import com.schedulerates.comparison.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.response.ComparisonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Schedule>} to {@link CustomPagingResponse<ScheduleResponse>}.
 */

@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    ComparisonToComparisonResponseMapper comparisonToComparisonResponseMapper = Mappers.getMapper(ComparisonToComparisonResponseMapper.class);

    /**
     * Converts a CustomPage<Comparison> object to CustomPagingResponse<ComparisonResponse>.
     *
     * @param comparisonPage The CustomPage<Comparison> object to convert.
     * @return CustomPagingResponse<ComparisonResponse> object containing mapped data.
     */
    default CustomPagingResponse<ComparisonResponse> toPagingResponse(CustomPage<Comparison> comparisonPage) {

        if (comparisonPage == null) {
            return null;
        }

        return CustomPagingResponse.<ComparisonResponse>builder()
                .content(toComparisonResponseList(comparisonPage.getContent()))
                .totalElementCount(comparisonPage.getTotalElementCount())
                .totalPageCount(comparisonPage.getTotalPageCount())
                .pageNumber(comparisonPage.getPageNumber())
                .pageSize(comparisonPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Comparison objects to a list of ComparisonResponse objects.
     *
     * @param comparisons The list of Comparison objects to convert.
     * @return List of ComparisonResponse objects containing mapped data.
     */
    default List<ComparisonResponse> toComparisonResponseList(List<Comparison> comparisons) {

        if (comparisons == null) {
            return null;
        }

        return comparisons.stream()
                .map(comparisonToComparisonResponseMapper::map)
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
