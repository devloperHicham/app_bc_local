package com.schedulerates.setting.model.transportation.dto.request;

import com.schedulerates.setting.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving Transportations as {@link TransportationPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TransportationPagingRequest extends CustomPagingRequest {
    private String search; // this will hold dtParams.search.value
}
