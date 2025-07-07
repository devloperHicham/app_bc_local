package com.schedulerates.schedule.model.schedule.dto.response;
import lombok.*;
import java.util.List;

/**
 * Represents a response object containing Comparison details as
 * {@link ComparisonResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyScheduleByCompaniesData {

    private String companyName;
    private List<Integer> schedules;
}
