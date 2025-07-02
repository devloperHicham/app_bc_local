package com.schedulerates.schedule.model.schedule.mapper;

import com.schedulerates.schedule.model.common.CustomPage;
import com.schedulerates.schedule.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.response.ScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Schedule>} to {@link CustomPagingResponse<ScheduleResponse>}.
 */

@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    ScheduleToScheduleResponseMapper scheduleToScheduleResponseMapper = Mappers.getMapper(ScheduleToScheduleResponseMapper.class);

    /**
     * Converts a CustomPage<Schedule> object to CustomPagingResponse<ScheduleResponse>.
     *
     * @param schedulePage The CustomPage<Schedule> object to convert.
     * @return CustomPagingResponse<ScheduleResponse> object containing mapped data.
     */
    default CustomPagingResponse<ScheduleResponse> toPagingResponse(CustomPage<Schedule> schedulePage) {

        if (schedulePage == null) {
            return null;
        }

        return CustomPagingResponse.<ScheduleResponse>builder()
                .content(toScheduleResponseList(schedulePage.getContent()))
                .totalElementCount(schedulePage.getTotalElementCount())
                .totalPageCount(schedulePage.getTotalPageCount())
                .pageNumber(schedulePage.getPageNumber())
                .pageSize(schedulePage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Schedule objects to a list of ScheduleResponse objects.
     *
     * @param schedules The list of Schedule objects to convert.
     * @return List of ScheduleResponse objects containing mapped data.
     */
    default List<ScheduleResponse> toScheduleResponseList(List<Schedule> schedules) {

        if (schedules == null) {
            return null;
        }

        return schedules.stream()
                .map(scheduleToScheduleResponseMapper::map)
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
