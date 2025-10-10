package com.schedulerates.comparison.model.comparison.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Represents a request object for creating a new client comparison entry.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientComparisonCreateRequest {

    // Foreign key IDs or codes
    @NotBlank(message = "Commodity code is required")
    private String commodityCode;

    // Numeric fields
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;

    @NotBlank(message = "weight type is required")
    private String weightType;

    // Optional field (can be null)
    private String infoDetail;

    // Boolean fields
    @NotNull(message = "Customs clearance flag is required")
    private Boolean customsClearance;

    @NotNull(message = "insurance flag is required")
    private Boolean insurance;
    
    @NotNull(message = "Certification flag is required")
    private Boolean certification;

    @NotNull(message = "Inspection service flag is required")
    private Boolean inspectionService;

    // Other fields
    @NotNull(message = "Transaction code is required")
    private Long codeTransation;

    @NotBlank(message = "Comparison ID is required")
    private String comparisonId;
}
