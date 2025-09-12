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
public class ComparisonClientPagingRequest extends CustomPagingRequest {
    private String selectedPortFromComparison;
    private String selectedPortToComparison;
    private String selectedCompany;
    private String searchOn;
    private String selectedDateComparison;
    private String weeksAhead;
    private Boolean isCheapest;
    private Boolean isFastest;
    private Boolean isDirect;  
}