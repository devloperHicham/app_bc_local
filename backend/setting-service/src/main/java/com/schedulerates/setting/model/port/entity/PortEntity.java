package com.schedulerates.setting.model.port.entity;

import com.schedulerates.setting.model.common.entity.BaseEntity;
import com.schedulerates.setting.model.port.entity.PortEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a Port as {@link PortEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ports")
public class PortEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "PORT_NAME")
    @NotBlank(message = "Port name can't be blank.")
    private String portName;

    @Column(name = "COUNTRY_NAME")
    @NotBlank(message = "Port name can't be blank.")
    private String countryName;

    @Column(name = "COUNTRY_NAME_ABBREVIATION")
    @NotBlank(message = "Port name abbreviation can't be blank.")
    private String countryNameAbbreviation;

    @Column(name = "PORT_CODE")
    @NotBlank(message = "Port name can't be blank.")
    private String portCode;

    @Column(name = "PORT_LONGITUDE")
    private Double portLongitude;

    @Column(name = "PORT_LATITUDE")
    private Double portLatitude;

    @Column(name = "PORT_LOGO")
    private String portLogo;

    @Column(name = "OBS")
    private String obs;

}
