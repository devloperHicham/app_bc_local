package com.schedulerates.schedule.model.schedule.mapper;

import com.schedulerates.schedule.model.common.mapper.BaseMapper;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ScheduleEntityToScheduleMapper} for converting {@link ScheduleEntity} to {@link Schedule}.
 */
@Mapper
public interface ScheduleEntityToScheduleMapper extends BaseMapper<ScheduleEntity, Schedule> {

    /**
     * Maps ScheduleEntity to Schedule.
     *
     * @param source The ScheduleEntity object to map.
     * @return Schedule object containing mapped data.
     */
    @Override
    Schedule map(ScheduleEntity source);

    /**
     * Initializes and returns an instance of ScheduleEntityToScheduleMapper.
     *
     * @return Initialized ScheduleEntityToScheduleMapper instance.
     */
    static ScheduleEntityToScheduleMapper initialize() {
        return Mappers.getMapper(ScheduleEntityToScheduleMapper.class);
    }

}
