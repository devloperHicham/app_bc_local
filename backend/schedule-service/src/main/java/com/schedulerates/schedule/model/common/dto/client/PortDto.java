package com.schedulerates.schedule.model.common.dto.client;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortDto {

    private String id;
    private String portName;
    private String countryName;
    private String portCode;
    private Number portLongitude;
    private Number portLatitude;
}