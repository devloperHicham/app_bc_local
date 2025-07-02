package com.schedulerates.schedule.model.schedule.dto.response;

import lombok.*;
import java.util.List;

/**
 * Represents a response object containing schedule details as
 * {@link ScheduleResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyScheduleData {

    private String week;
    private List<Integer> schedules;
}