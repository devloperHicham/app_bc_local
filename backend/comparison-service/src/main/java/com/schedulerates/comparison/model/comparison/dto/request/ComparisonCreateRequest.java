package com.schedulerates.comparison.model.comparison.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Represents a request object for creating a new schedule entry.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComparisonCreateRequest {

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

    @NotNull(message = "Container IDs are required")
    private List<String> containerId;

    @NotNull(message = "Departure dates are required")
    private List<String> dateDepart;

    @NotNull(message = "Arrival dates are required")
    private List<String> dateArrive;

    @NotNull(message = "Prices are required")
    private List<Integer> price;
}
