package com.schedulerates.client.model.client;

import com.schedulerates.client.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a client as {@link client}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseDomainModel {

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

}
