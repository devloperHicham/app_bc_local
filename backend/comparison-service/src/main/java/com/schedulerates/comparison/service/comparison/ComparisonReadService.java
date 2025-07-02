package com.schedulerates.comparison.service.comparison;

import com.schedulerates.comparison.model.common.CustomPage;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonPagingRequest;

/**
 * Service interface named {@link TransportationReadService} for reading Transportations.
 */
public interface ComparisonReadService {

    /**
     * Retrieves a Comparison by its unique ID.
     *
     * @param comparisonId The ID of the Comparison to retrieve.
     * @return The Comparison object corresponding to the given ID.
     */
    Comparison getComparisonById(final String comparisonId);

    /**
     * Retrieves a page of Comparisons based on the paging request criteria.
     *
     * @param comparisonPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of Comparisons that match the paging criteria.
     */
    CustomPage<Comparison> getComparisons(final ComparisonPagingRequest comparisonPagingRequest);

}
