package com.schedulerates.comparison.model.comparison;

import com.schedulerates.comparison.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a Comparison as {@link Comparison}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClientComparison extends BaseDomainModel {

    private String id;

    // Foreign keys
    private String portFromId;
    private String portFromName;
    private String countryFromName;
    private String countryFromNameAbbreviation;
    private String portFromCode;
    private Double portFromLongitude;
    private Double portFromLatitude;
    private String portFromLogo;

    private String portToId;
    private String portToName;
    private String countryToName;
    private String countryToNameAbbreviation;
    private String portToCode;
    private Double portToLongitude;
    private Double portToLatitude;
    private String portToLogo;

    private String companyId;
    private String companyName;
    private String companyLogo;
    private String transportationId;
    private String transportationName;
    private String gargoId;
    private String gargoName;
    private String containerId;
    private String containerName;
    private Integer containerWeight;

    // Schedule information
    private String dateDepart;
    private String dateArrive;
    private Integer price;
    private Long codeTransation;

    // Additional fields from ClientComparisonCreateRequest
    private String commodityCode;
    private Double weight;
    private String weightType;
    private String infoDetail;
    private String insurance;
    private Boolean customsClearance;
    private Boolean certification;
    private Boolean inspectionService;
}
