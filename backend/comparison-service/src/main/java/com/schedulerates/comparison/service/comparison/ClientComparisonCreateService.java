package com.schedulerates.comparison.service.comparison;

import com.schedulerates.comparison.model.comparison.ClientComparison;
import com.schedulerates.comparison.model.comparison.dto.request.ClientComparisonCreateRequest;

import java.util.List;

/**
 * Service interface named {@link ClientComparisonCreateService} for creating Comparisons.
 */
public interface ClientComparisonCreateService {

    /**
     * Creates new Comparisons based on the provided Comparison creation request.
     * Creates multiple records by combining container/price pairs with all date pairs.
     *
     * @param clientComparisonCreateRequest The request containing data to create the comparisons.
     * @return List of created Comparison objects.
     */
    List<ClientComparison> createComparison(final ClientComparisonCreateRequest clientComparisonCreateRequest);
}