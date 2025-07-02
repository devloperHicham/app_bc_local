package com.schedulerates.schedule.service.schedule;

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

}
