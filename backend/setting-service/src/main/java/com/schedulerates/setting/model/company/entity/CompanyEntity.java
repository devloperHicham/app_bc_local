package com.schedulerates.setting.model.company.entity;

import com.schedulerates.setting.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a company as {@link CompanyEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class CompanyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "COMPANY_NAME")
    @NotBlank(message = "Company name can't be blank.")
    private String companyName;

    @Column(name = "COMPANY_LOGO")
    @NotBlank(message = "Company logo can't be blank.")
    private String companyLogo;

    @Column(name = "OBS")
    private String obs;
}