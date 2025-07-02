package com.schedulerates.comparison.service.comparison;

import java.util.List;

/**
 * Service interface named {@link ScheduleDeleteService} for deleting Schedules.
 */
public interface ComparisonDeleteService {

    /**
     * Deletes a Comparison identified by its unique ID.
     *
     * @param comparisonId The ID of the Comparison to delete.
     */
    void deleteComparisonById(final String comparisonId);

    /**
     * Deletes a Comparison identified by multiple ID.
     *
     * @param comparisonId The ID of the Comparison to delete.
     */
    void deleteComparisonsByIds(List<String> comparisonIds);
}
