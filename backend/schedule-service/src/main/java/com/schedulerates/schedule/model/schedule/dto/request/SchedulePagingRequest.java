package com.schedulerates.schedule.model.schedule.dto.request;

import com.schedulerates.schedule.model.common.dto.request.CustomPagingRequest;
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
public class SchedulePagingRequest extends CustomPagingRequest {
    private String search; // this will hold dtParams.search.value
    private String portFromId;
    private String portToId;
    private String dateDepart;
    private String dateArrive;
    private String companyId;
}
