package com.schedulerates.schedule.service.schedule;

import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleUpdateRequest;

/**
 * Service interface named {@link ScheduleUpdateService} for updating Schedules.
 */
public interface ScheduleUpdateService {

    /**
     * Updates a Schedule identified by its unique ID using the provided update request.
     *
     * @param scheduleId           The ID of the Schedule to update.
     * @param scheduleUpdateRequest The request containing updated data for the Schedule.
     * @return The updated Schedule object.
     */
    Schedule updateScheduleById(final String scheduleId, final ScheduleUpdateRequest scheduleUpdateRequest);

}
