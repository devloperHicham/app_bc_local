package com.schedulerates.setting.model.gargo.dto.request;

import com.schedulerates.setting.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving gargos as {@link gargoPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class GargoPagingRequest extends CustomPagingRequest {
    private String search; // this will hold dtParams.search.value
}