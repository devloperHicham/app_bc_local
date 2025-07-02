package com.schedulerates.comparison.service.comparison;

import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonCreateRequest;

import java.util.List;

/**
 * Service interface named {@link ComparisonCreateService} for creating Comparisons.
 */
public interface ComparisonCreateService {

    /**
     * Creates new Comparisons based on the provided Comparison creation request.
     * Creates multiple records by combining container/price pairs with all date pairs.
     *
     * @param comparisonCreateRequest The request containing data to create the comparisons.
     * @return List of created Comparison objects.
     */
    List<Comparison> createComparison(final ComparisonCreateRequest comparisonCreateRequest);
}