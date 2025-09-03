package com.schedulerates.comparison.model.comparison.dto.response;
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
public class DailyComparisonByUsersData {

    private String userName;
    private Integer comparisons;
}

    
