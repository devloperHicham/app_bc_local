package com.schedulerates.schedule.model.schedule.mapper;

import com.schedulerates.schedule.model.common.mapper.BaseMapper;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.response.ScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link TransportationToTransportationResponseMapper} for converting {@link Transportation} to {@link TransportationResponse}.
 */
@Mapper
public interface ScheduleToScheduleResponseMapper extends BaseMapper<Schedule, ScheduleResponse> {

    /**
     * Maps Schedule to ScheduleResponse.
     *
     * @param source The Schedule object to map.
     * @return ScheduleResponse object containing mapped data.
     */
    @Override
    ScheduleResponse map(Schedule source);

    /**
     * Initializes and returns an instance of ScheduleToScheduleResponseMapper.
     *
     * @return Initialized ScheduleToScheduleResponseMapper instance.
     */
    static ScheduleToScheduleResponseMapper initialize() {
        return Mappers.getMapper(ScheduleToScheduleResponseMapper.class);
    }

}
