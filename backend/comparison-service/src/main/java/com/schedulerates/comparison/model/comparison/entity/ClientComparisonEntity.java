package com.schedulerates.comparison.model.comparison.entity;

import com.schedulerates.comparison.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a Comparison as
 * {@link ComparisonEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_comparisons")
public class ClientComparisonEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    // Foreign keys
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

    @Column(name = "TRANSPORTATION_ID", nullable = false)
    private String transportationId;

    @Column(name = "TRANSPORTATION_NAME", nullable = false)
    private String transportationName;

    @Column(name = "GARGO_ID", nullable = false)
    private String gargoId;

    @Column(name = "GARGO_NAME", nullable = false)
    private String gargoName;

    @Column(name = "CONTAINER_ID", nullable = false)
    private String containerId;

    @Column(name = "CONTAINER_NAME", nullable = false)
    private String containerName;

    @Column(name = "CONTAINER_WEIGHT", nullable = false)
    private Integer containerWeight;
    // Schedule information
    @Column(name = "DATE_DEPART", nullable = false)
    private String dateDepart;

    @Column(name = "DATE_ARRIVE", nullable = false)
    private String dateArrive;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "CODE_TRANSATION", nullable = false)
    private Long codeTransation;

    @Column(name = "ACTIVE")
    private String active;

    @Column(name = "COMMODITY_CODE", nullable = false)
    private String commodityCode;

    @Column(name = "WEIGHT", nullable = false)
    private Double weight;

    @Column(name = "WEIGHT_TYPE")
    private String weightType;

    @Column(name = "INFO_DETAIL", nullable = false)
    private String infoDetail;

    @Column(name = "CUSTOMS_CLEARANCE", nullable = false)
    private Boolean customsClearance;

    @Column(name = "INSURANCE", nullable = false)
    private Boolean insurance;

    @Column(name = "CERTIFICATION", nullable = false)
    private Boolean certification;

    @Column(name = "INSPECTION_SERVICE", nullable = false)
    private Boolean inspectionService;
}
