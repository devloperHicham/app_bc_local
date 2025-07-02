package com.schedulerates.setting.model.container.dto.response;

import lombok.*;

/**
 * Represents a response object containing Container details as {@link ContainerResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerResponse {

    private String id;
    private String containerName;
    private Integer containerWeight;
    private String obs;

}