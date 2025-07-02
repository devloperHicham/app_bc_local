package com.schedulerates.schedule.model.schedule.dto.request;

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
public class ScheduleCreateRequest {

    @NotNull(message = "Port from ID is required")
    private String portFromId;  // ID from port table in another service
    
    @NotNull(message = "Port to ID is required")
    private String portToId;     // ID from port table in another service
    
    @NotNull(message = "Company ID is required")
    private String companyId;    // ID from company table in another service
    
    @NotNull(message = "Departure date is required")
    private List<String> dateDepart;  // Departure date
    
    @NotNull(message = "Arrival date is required")
    private List<String> dateArrive;  // Arrival date
    
    @NotNull(message = "Transit days are required")
    private List<Integer>  transit;   // Number of days in transit
    
    @NotNull(message = "Vessel reference is required")
    private List<String> vessel;     // Vessel reference
    
    @NotNull(message = "Voyage reference is required")
    private List<String> refVoyage;  // Voyage reference
    
    private List<String> serviceName; // Name of the service
}