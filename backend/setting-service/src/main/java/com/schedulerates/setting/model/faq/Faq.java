package com.schedulerates.setting.model.faq;

import com.schedulerates.setting.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a domain model for a FAQ as {@link Faq}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Faq extends BaseDomainModel {

    private String id;
    private String fullName;
    private String typeFaq;
    private String active;
    private String obs; // Optional field

}