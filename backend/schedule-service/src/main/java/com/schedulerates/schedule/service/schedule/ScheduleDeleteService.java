package com.schedulerates.schedule.service.schedule;

import java.util.List;

/**
 * Service interface named {@link ScheduleDeleteService} for deleting Schedules.
 */
public interface ScheduleDeleteService {

    /**
     * Deletes a Schedule identified by its unique ID.
     *
     * @param scheduleId The ID of the Schedule to delete.
     */
    void deleteScheduleById(final String scheduleId);

    
    /**
     * Deletes a schedule identified by multiple ID.
     *
     * @param scheduleIdS The ID of the schedule to delete.
     */
    void deleteSchedulesByIds(List<String> scheduleIds);
}
