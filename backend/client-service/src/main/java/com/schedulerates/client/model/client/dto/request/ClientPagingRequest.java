package com.schedulerates.client.model.client.dto.request;

import com.schedulerates.client.model.common.dto.request.CustomPagingRequest;
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
public class ClientPagingRequest extends CustomPagingRequest {
    private String search;
    private String portFromId;
    private String portToId;
    private String dateDepart;
    private String dateArrive;
    private String companyId;
}
