package com.schedulerates.comparison.model.comparison.dto.request;

import com.schedulerates.comparison.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving schedule entries as
 * {@link schedulePagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ComparisonPagingRequest extends CustomPagingRequest {
    private String search;
    private String portFromId;
    private String portToId;
    private String dateDepart;
    private String dateArrive;
    private String companyId;
}
