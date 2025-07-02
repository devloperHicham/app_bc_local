package com.schedulerates.setting.model.transportation.entity;

import com.schedulerates.setting.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a Transportation as {@link TransportationEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transportations")
public class TransportationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "TRANSPORTATION_NAME")
    @NotBlank(message = "transportation name can't be blank.")
    private String transportationName;

    @Column(name = "OBS")
    private String obs;

}
