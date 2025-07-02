package com.schedulerates.schedule.model.schedule.mapper;

import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListTransportationEntityToListTransportationMapper} for converting {@link List<TransportationEntity>} to {@link List<Transportation>}.
 */
@Mapper
public interface ListScheduleEntityToListScheduleMapper {

    ScheduleEntityToScheduleMapper scheduleEntityToScheduleMapper = Mappers.getMapper(ScheduleEntityToScheduleMapper.class);

    /**
     * Converts a list of ScheduleEntity objects to a list of Schedule objects.
     *
     * @param scheduleEntities The list of ScheduleEntity objects to convert.
     * @return List of Schedule objects containing mapped data.
     */
    default List<Schedule> toScheduleList(List<ScheduleEntity> scheduleEntities) {

        if (scheduleEntities == null) {
            return null;
        }

        return scheduleEntities.stream()
                .map(scheduleEntityToScheduleMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListScheduleEntityToListScheduleMapper.
     *
     * @return Initialized ListScheduleEntityToListScheduleMapper instance.
     */
    static ListScheduleEntityToListScheduleMapper initialize() {
        return Mappers.getMapper(ListScheduleEntityToListScheduleMapper.class);
    }

}
