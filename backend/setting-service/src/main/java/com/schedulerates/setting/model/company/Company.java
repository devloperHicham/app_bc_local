package com.schedulerates.setting.model.company;

import com.schedulerates.setting.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a company as {@link Company}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseDomainModel {

    private String id;
    private String companyName;
    private String companyLogo;
    private String obs;

}