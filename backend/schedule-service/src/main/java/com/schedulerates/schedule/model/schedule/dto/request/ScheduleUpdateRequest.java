package com.schedulerates.schedule.model.schedule.dto.request;

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
public class ScheduleUpdateRequest {


    @NotNull(message = "Port from ID is required")
    private String portFromId;  // ID from port table in another service
    
    @NotNull(message = "Port to ID is required")
    private String portToId;     // ID from port table in another service
    
    @NotNull(message = "Company ID is required")
    private String companyId;    // ID from company table in another service
    
    @NotNull(message = "Departure date is required")
    private String dateDepart;  // Departure date
    
    @NotNull(message = "Arrival date is required")
    private String dateArrive;  // Arrival date
    
    @NotNull(message = "Transit days are required")
    private Integer transit;   // Number of days in transit
    
    @NotNull(message = "Vessel reference is required")
    private String vessel;     // Vessel reference
    
    @NotNull(message = "Voyage reference is required")
    private String refVoyage;  // Voyage reference
    
    private String serviceName; // Name of the service

}