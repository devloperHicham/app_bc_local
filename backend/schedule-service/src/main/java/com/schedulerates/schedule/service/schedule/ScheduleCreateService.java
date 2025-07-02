package com.schedulerates.schedule.service.schedule;

import java.util.List;

import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleCreateRequest;

/**
 * Service interface named {@link TransportationCreateService} for creating Transportations.
 */
public interface ScheduleCreateService {

    /**
     * Creates a new Schedule based on the provided Schedule creation request.
     *
     * @param scheduleCreateRequest The request containing data to create the Schedule.
     * @return The created Schedule object.
     */
    List<Schedule> createSchedule(final ScheduleCreateRequest scheduleCreateRequest);

}
