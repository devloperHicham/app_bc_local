package com.schedulerates.schedule.model.schedule.entity;

import com.schedulerates.schedule.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a Transportation as
 * {@link TransportationEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class ScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "PORT_FROM_ID", nullable = false)
    private String portFromId;

    @Column(name = "PORT_FROM_NAME", nullable = false)
    private String portFromName;

    @Column(name = "COUNTRY_FROM_NAME")
    private String countryFromName;

    @Column(name = "COUNTRY_FROM_NAME_ABBREVIATION")
    private String countryFromNameAbbreviation;

    @Column(name = "PORT_FROM_CODE")
    private String portFromCode;

    @Column(name = "PORT_FROM_LONGITUDE")
    private Double portFromLongitude;

    @Column(name = "PORT_FROM_LATITUDE")
    private Double portFromLatitude;

    @Column(name = "PORT_FROM_LOGO")
    private String portFromLogo;

    @Column(name = "PORT_TO_ID", nullable = false)
    private String portToId;

    @Column(name = "PORT_TO_NAME", nullable = false)
    private String portToName;

    @Column(name = "COUNTRY_TO_NAME")
    private String countryToName;

    @Column(name = "COUNTRY_TO_NAME_ABBREVIATION")
    private String countryToNameAbbreviation;

    @Column(name = "PORT_To_CODE")
    private String portToCode;

    @Column(name = "PORT_TO_LONGITUDE")
    private Double portToLongitude;

    @Column(name = "PORT_TO_LATITUDE")
    private Double portToLatitude;

    @Column(name = "PORT_TO_LOGO")
    private String portToLogo;

    @Column(name = "COMPANY_ID", nullable = false)
    private String companyId;

    @Column(name = "COMPANY_NAME", nullable = false)
    private String companyName;

    @Column(name = "COMPANY_LOGO", nullable = false)
    private String companyLogo;

    @Column(name = "DATE_DEPART", nullable = false)
    private String dateDepart;

    @Column(name = "DATE_ARRIVE", nullable = false)
    private String dateArrive;

    @Column(name = "TRANSIT_DAYS", nullable = false)
    private Integer transit;

    @Column(name = "VESSEL", nullable = false, length = 100)
    private String vessel;

    @Column(name = "REF_VOYAGE", nullable = false, length = 50)
    private String refVoyage;

    @Column(name = "SERVICE_NAME", nullable = false, length = 200)
    private String serviceName;

    @Column(name = "ACTIVE")
    private String active;

    @Override
    @PrePersist
    public void prePersist() {
        if (this.active == null) {
            this.active = "1"; // Only set default if not already set
        }
    }

}
