package com.schedulerates.setting.model.container.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a request object for updating an existing Container as
 * {@link ContainerUpdateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContainerUpdateRequest {

    @Size(min = 3, message = "Container name can't be blank.")
    private String containerName;

    @NotNull(message = "Container weight is required.")
    @Min(value = 1, message = "Container weight must be at least 1.")
    private Integer containerWeight;

    private String obs;

}