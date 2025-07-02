package com.schedulerates.setting.model.container.mapper;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.response.ContainerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Container>} to {@link CustomPagingResponse<ContainerResponse>}.
 */

@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    ContainerToContainerResponseMapper containerToContainerResponseMapper = Mappers.getMapper(ContainerToContainerResponseMapper.class);

    /**
     * Converts a CustomPage<Container> object to CustomPagingResponse<ContainerResponse>.
     *
     * @param ContainerPage The CustomPage<Container> object to convert.
     * @return CustomPagingResponse<ContainerResponse> object containing mapped data.
     */
    default CustomPagingResponse<ContainerResponse> toPagingResponse(CustomPage<Container> containerPage) {

        if (containerPage == null) {
            return null;
        }

        return CustomPagingResponse.<ContainerResponse>builder()
                .content(toContainerResponseList(containerPage.getContent()))
                .totalElementCount(containerPage.getTotalElementCount())
                .totalPageCount(containerPage.getTotalPageCount())
                .pageNumber(containerPage.getPageNumber())
                .pageSize(containerPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Container objects to a list of ContainerResponse objects.
     *
     * @param Containers The list of Container objects to convert.
     * @return List of ContainerResponse objects containing mapped data.
     */
    default List<ContainerResponse> toContainerResponseList(List<Container> containers) {

        if (containers == null) {
            return null;
        }

        return containers.stream()
                .map(containerToContainerResponseMapper::map)
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