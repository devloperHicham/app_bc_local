package com.schedulerates.comparison.service.comparison;

import java.util.List;

import com.schedulerates.comparison.model.comparison.dto.response.DashboardResponse;
import com.schedulerates.comparison.model.comparison.dto.response.WeeklyComparisonData;

/**
 * Service interface named {@link TransportationReadService} for reading Transportations.
 */
public interface ComparisonDashboardService {

    DashboardResponse getComparisonStats();
    
    List<WeeklyComparisonData> getGraphicComparisons();

}
