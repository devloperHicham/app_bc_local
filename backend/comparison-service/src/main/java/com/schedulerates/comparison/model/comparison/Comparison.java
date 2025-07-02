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
public class Comparison extends BaseDomainModel {

    private String id;

    // Foreign keys
    private String portFromId;
    private String portToId;
    private String companyId;
    private String transportationId;
    private String gargoId;
    private String containerId;

    private String portFromName;
    private String portToName;
    private String companyName;
    private String transportationName;
    private String gargoName;
    private String containerName;

    // Schedule information
    private String dateDepart;
    private String dateArrive;
    private Integer price;

}
