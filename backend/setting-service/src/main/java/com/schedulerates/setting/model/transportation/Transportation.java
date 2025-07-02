package com.schedulerates.setting.model.transportation;

import com.schedulerates.setting.model.common.BaseDomainModel;
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
public class Transportation extends BaseDomainModel {

    private String id;
    private String transportationName;
    private String obs; // Optional field

}