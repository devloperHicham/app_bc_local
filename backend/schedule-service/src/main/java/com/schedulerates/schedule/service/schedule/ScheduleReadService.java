package com.schedulerates.schedule.service.schedule;

import com.schedulerates.schedule.model.common.CustomPage;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.SchedulePagingRequest;

/**
 * Service interface named {@link TransportationReadService} for reading Transportations.
 */
public interface ScheduleReadService {

    /**
     * Retrieves a Schedule by its unique ID.
     *
     * @param scheduleId The ID of the Schedule to retrieve.
     * @return The Schedule object corresponding to the given ID.
     */
    Schedule getScheduleById(final String scheduleId);

    /**
     * Retrieves a page of Schedules based on the paging request criteria.
     *
     * @param schedulePagingRequest The paging request criteria.
     * @return A CustomPage containing the list of Schedules that match the paging criteria.
     */
    CustomPage<Schedule> getSchedules(final SchedulePagingRequest schedulePagingRequest);

}
