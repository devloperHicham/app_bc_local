package com.schedulerates.schedule.service.schedule;

import com.schedulerates.schedule.model.common.CustomPage;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.SchedulePagingHistoriqueRequest;

/**
 * Service interface named {@link TransportationReadService} for reading
 * Transportations.
 */
public interface ScheduleHistoriqueService {

    /**
     * Retrieves a page of Schedules based on the paging request criteria.
     *
     * @param schedulePagingHistoriqueRequest The paging request criteria.
     * @return A CustomPage containing the list of Schedules that match the paging
     *         criteria.
     */
    CustomPage<Schedule> getHistoriques(final SchedulePagingHistoriqueRequest schedulePagingHistoriqueRequest);

}
