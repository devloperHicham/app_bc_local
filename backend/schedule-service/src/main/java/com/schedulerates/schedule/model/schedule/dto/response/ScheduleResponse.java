package com.schedulerates.schedule.model.schedule.dto.response;

import lombok.*;

/**
 * Represents a response object containing schedule details as
 * {@link ScheduleResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    
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

    // Schedule information
    private String dateDepart;
    private String dateArrive;
    private Integer transit;
    private Integer codeTransation;

    // Vessel and service info
    private String vessel;
    private String refVoyage;
    private String serviceName;
}