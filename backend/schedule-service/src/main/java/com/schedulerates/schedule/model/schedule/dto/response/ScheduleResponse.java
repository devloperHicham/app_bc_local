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
    private String portToId;
    private String companyId;

    private String portFromName;
    private String portToName;
    private String companyName;

    // Schedule information
    private String dateDepart;
    private String dateArrive;
    private Integer transit;

    // Vessel and service info
    private String vessel;
    private String refVoyage;
    private String serviceName;

}