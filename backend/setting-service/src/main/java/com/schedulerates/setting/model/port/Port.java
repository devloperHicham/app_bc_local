package com.schedulerates.setting.model.port;

import com.schedulerates.setting.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a Port as {@link Port}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Port extends BaseDomainModel {

    private String id;
    private String portName;
    private String countryName;
    private String countryNameAbbreviation;
    private String portCode;
    private Double portLongitude;
    private Double portLatitude;
    private String portLogo;
    private String obs;

}