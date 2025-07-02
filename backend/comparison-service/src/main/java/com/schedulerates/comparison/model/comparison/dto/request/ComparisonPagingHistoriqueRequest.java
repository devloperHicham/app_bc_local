package com.schedulerates.comparison.model.comparison.dto.request;

import com.schedulerates.comparison.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving schedule entries as
 * {@link ComparisonPagingHistoriqueRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ComparisonPagingHistoriqueRequest extends CustomPagingRequest {

    private String search; // this will hold dtParams.search.value
    private String portFromId;
    private String portToId;
    private String dateDebut;
    private String dateFin;
    private String userId;
}
