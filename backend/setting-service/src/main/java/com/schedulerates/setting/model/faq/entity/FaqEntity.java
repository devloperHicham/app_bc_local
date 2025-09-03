package com.schedulerates.setting.model.faq.entity;

import com.schedulerates.setting.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a FAQ as {@link FaqEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faqs")
public class FaqEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "FULL_NAME")
    @NotBlank(message = "Full name can't be blank.")
    private String fullName;

    @Column(name = "TYPE_FAQ")
    @NotBlank(message = "Type of FAQ can't be blank.")
    private String typeFaq;

    @Builder.Default
    @Column(name = "ACTIVE")
    private String active = "1";

    @Builder.Default
    @Column(name = "READ")
    private Boolean read = false;

    @Column(name = "OBS")
    @Size(min = 5, max = 500, message = "Observation must be between 10 and 500 characters.")
    private String obs;
}