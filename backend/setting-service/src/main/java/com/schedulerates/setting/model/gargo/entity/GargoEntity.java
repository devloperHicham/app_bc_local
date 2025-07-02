package com.schedulerates.setting.model.gargo.entity;

import com.schedulerates.setting.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a gargo as {@link gargoEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gargos")
public class GargoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "GARGO_NAME")
    @NotBlank(message = "Full name can't be blank.")
    private String gargoName;

    @Column(name = "OBS")
    private String obs;
}