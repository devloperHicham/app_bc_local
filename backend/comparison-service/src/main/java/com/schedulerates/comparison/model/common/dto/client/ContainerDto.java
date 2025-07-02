package com.schedulerates.comparison.model.common.dto.client;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerDto {
    
    private String id;
    private String containerName;
    private Integer containerWeight;
}
