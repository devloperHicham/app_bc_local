package com.schedulerates.schedule.service.schedule;

import java.util.List;

import com.schedulerates.schedule.model.schedule.dto.response.DashboardResponse;
import com.schedulerates.schedule.model.schedule.dto.response.WeeklyScheduleData;

/**
 * Service interface named {@link TransportationReadService} for reading Transportations.
 */
public interface ScheduleDashboardService {

    DashboardResponse getScheduleStats();
    
    List<WeeklyScheduleData> getGraphicSchedules();

}
