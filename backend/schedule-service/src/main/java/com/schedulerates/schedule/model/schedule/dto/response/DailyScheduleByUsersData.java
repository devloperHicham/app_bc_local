package com.schedulerates.schedule.model.schedule.dto.response;
import lombok.*;

/**
 * Represents a response object containing Comparison details as
 * {@link ComparisonResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyScheduleByUsersData {

    private String userName;
    private Integer schedules;
}

    
