package com.schedulerates.setting.model.container;

import com.schedulerates.setting.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a Container as {@link Container}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Container extends BaseDomainModel {

    private String id;
    private String containerName;
    private Integer containerWeight;
    private String obs;

}