package com.schedulerates.setting.model.company.dto.request;

import com.schedulerates.setting.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving companies as {@link CompanyPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CompanyPagingRequest extends CustomPagingRequest {
    private String search; // this will hold dtParams.search.value
}