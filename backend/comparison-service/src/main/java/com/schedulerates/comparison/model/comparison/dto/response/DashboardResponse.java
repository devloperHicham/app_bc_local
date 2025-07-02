package com.schedulerates.comparison.model.comparison.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    
    private Integer totalAdminComparisons;
    private Integer totalAdminScoreComparisons;
    private Integer totalUserComparisonTodays;
    private Integer totalUserComparisonYesterdays;
    private Integer scoreUserComparisons;
}