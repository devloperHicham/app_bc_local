package com.schedulerates.comparison.service.comparison;

import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonUpdateRequest;

/**
 * Service interface named {@link ScheduleUpdateService} for updating Schedules.
 */
public interface ComparisonUpdateService {

    /**
     * Updates a Comparison identified by its unique ID using the provided update request.
     *
     * @param comparisonId           The ID of the Comparison to update.
     * @param comparisonUpdateRequest The request containing updated data for the comparison.
     * @return The updated Comparison object.
     */
    Comparison updateComparisonById(final String comparisonId, final ComparisonUpdateRequest comparisonUpdateRequest);

}
