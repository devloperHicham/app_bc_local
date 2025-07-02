package com.schedulerates.comparison.model.comparison.dto.response;

import lombok.*;

/**
 * Represents a response object containing schedule details as
 * {@link ComparisonResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonResponse {

    private String id;

    // Foreign keys
    private String portFromId;
    private String portToId;
    private String companyId;
    private String transportationId;
    private String gargoId;
    private String containerId;

    private String portFromName;
    private String portToName;
    private String companyName;
    private String transportationName;
    private String gargoName;
    private String containerName;

    // Schedule information
    private String dateDepart;
    private String dateArrive;
    private Integer price;

}
