package com.schedulerates.comparison.model.comparison.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a request object for updating an existing Transportation as
 * {@link TransportationUpdateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComparisonUpdateRequest {

    // Foreign key IDs
    @NotNull(message = "Port from ID is required")
    private String portFromId;

    @NotNull(message = "Port to ID is required")
    private String portToId;

    @NotNull(message = "Company ID is required")
    private String companyId;

    @NotNull(message = "Transportation ID is required")
    private String transportationId;

    @NotNull(message = "Gargo ID is required")
    private String gargoId;

    @NotNull(message = "Container ID is required")
    private String containerId;

    // Schedule info
    @NotNull(message = "Departure date is required")
    private String dateDepart;

    @NotNull(message = "Arrival date is required")
    private String dateArrive;

    @NotNull(message = "Price is required")
    private Integer price;
}
