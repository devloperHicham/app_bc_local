package com.schedulerates.schedule.model.schedule;

import com.schedulerates.schedule.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a Transportation as {@link Transportation}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseDomainModel {


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