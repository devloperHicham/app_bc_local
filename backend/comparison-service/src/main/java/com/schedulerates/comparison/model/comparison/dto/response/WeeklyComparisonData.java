package com.schedulerates.comparison.model.comparison.dto.response;

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
public class WeeklyComparisonData {

    private String week;
    private List<Integer> comparisons;
}