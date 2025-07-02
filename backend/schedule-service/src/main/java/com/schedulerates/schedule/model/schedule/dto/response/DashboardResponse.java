package com.schedulerates.schedule.model.schedule.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    
    private Integer totalAdminSchedules;
    private Integer totalAdminScoreSchedules;
    private Integer totalUserScheduleTodays;
    private Integer totalUserScheduleYesterdays;
    private Integer scoreUserSchedules;
}