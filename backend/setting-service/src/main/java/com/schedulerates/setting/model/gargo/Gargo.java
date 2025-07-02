package com.schedulerates.setting.model.gargo;

import com.schedulerates.setting.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a FAQ as {@link Transportation}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Gargo extends BaseDomainModel {

    private String id;
    private String gargoName;
    private String obs; // Optional field

}
