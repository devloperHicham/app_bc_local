package com.schedulerates.setting.model.transportation.mapper;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.response.TransportationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Transportation>} to {@link CustomPagingResponse<TransportationResponse>}.
 */

@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    TransportationToTransportationResponseMapper transportationToTransportationResponseMapper = Mappers.getMapper(TransportationToTransportationResponseMapper.class);

    /**
     * Converts a CustomPage<Transportation> object to CustomPagingResponse<TransportationResponse>.
     *
     * @param transportationPage The CustomPage<Transportation> object to convert.
     * @return CustomPagingResponse<TransportationResponse> object containing mapped data.
     */
    default CustomPagingResponse<TransportationResponse> toPagingResponse(CustomPage<Transportation> transportationPage) {

        if (transportationPage == null) {
            return null;
        }

        return CustomPagingResponse.<TransportationResponse>builder()
                .content(toTransportationResponseList(transportationPage.getContent()))
                .totalElementCount(transportationPage.getTotalElementCount())
                .totalPageCount(transportationPage.getTotalPageCount())
                .pageNumber(transportationPage.getPageNumber())
                .pageSize(transportationPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Transportation objects to a list of TransportationResponse objects.
     *
     * @param transportations The list of Transportation objects to convert.
     * @return List of TransportationResponse objects containing mapped data.
     */
    default List<TransportationResponse> toTransportationResponseList(List<Transportation> transportations) {

        if (transportations == null) {
            return null;
        }

        return transportations.stream()
                .map(transportationToTransportationResponseMapper::map)
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
