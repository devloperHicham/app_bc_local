package com.schedulerates.comparison.service.comparison;

import com.schedulerates.comparison.model.common.CustomPage;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonPagingHistoriqueRequest;


public interface ComparisonHistoriqueService {

    /**ComparisonHistoriqueService
     * Retrieves a page of Comparisons based on the paging request criteria.
     *
     * @param ComparisonPagingHistoriqueRequest The paging request criteria.
     * @return A CustomPage containing the list of Comparisons that match the paging
     *         criteria.
     */
    CustomPage<Comparison> getHistoriques(final ComparisonPagingHistoriqueRequest comparisonPagingHistoriqueRequest);

}
