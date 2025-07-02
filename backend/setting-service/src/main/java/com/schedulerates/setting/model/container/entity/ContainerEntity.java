package com.schedulerates.setting.model.container.entity;

import com.schedulerates.setting.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a persistent entity for a Container as {@link ContainerEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "containers")
public class ContainerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "CONTAINER_NAME")
    @NotBlank(message = "Name can't be blank.")
    private String containerName;

    @Column(name = "CONTAINER_WEIGHT")
    @NotNull(message = "Weight can't be null.")
    @Min(value = 1, message = "Weight must be at least 1.")
    private Integer containerWeight;

    @Column(name = "OBS")
    private String obs;
}