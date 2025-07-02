package com.schedulerates.setting.model.port.mapper;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.response.PortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Company>} to {@link CustomPagingResponse<CompanyResponse>}.
 */
@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    PortToPortResponseMapper portToPortResponseMapper = Mappers.getMapper(PortToPortResponseMapper.class);

    /**
     * Converts a CustomPage<Port> object to CustomPagingResponse<PortResponse>.
     *
     * @param portPage The CustomPage<Port> object to convert.
     * @return CustomPagingResponse<PortResponse> object containing mapped data.
     */
    default CustomPagingResponse<PortResponse> toPagingResponse(CustomPage<Port> portPage) {

        if (portPage == null) {
            return null;
        }

        return CustomPagingResponse.<PortResponse>builder()
                .content(toPortResponseList(portPage.getContent()))
                .totalElementCount(portPage.getTotalElementCount())
                .totalPageCount(portPage.getTotalPageCount())
                .pageNumber(portPage.getPageNumber())
                .pageSize(portPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Port objects to a list of PortResponse objects.
     *
     * @param ports The list of Port objects to convert.
     * @return List of PortResponse objects containing mapped data.
     */
    default List<PortResponse> toPortResponseList(List<Port> ports) {

        if (ports == null) {
            return null;
        }

        return ports.stream()
                .map(portToPortResponseMapper::map)
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
