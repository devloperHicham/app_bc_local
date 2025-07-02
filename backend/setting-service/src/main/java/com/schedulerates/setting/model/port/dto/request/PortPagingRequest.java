package com.schedulerates.setting.model.port.dto.request;

import com.schedulerates.setting.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving companies as {@link portPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PortPagingRequest extends CustomPagingRequest {
    private String search; // this will hold dtParams.search.value
}
