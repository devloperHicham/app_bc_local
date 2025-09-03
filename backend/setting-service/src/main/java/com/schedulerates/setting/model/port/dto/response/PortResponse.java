package com.schedulerates.setting.model.port.dto.response;

import lombok.*;

/**
 * Represents a response object containing Port details as {@link PortResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortResponse {

    private String id;
    private String portName;
    private String countryName;
    private String countryNameAbbreviation;
    private String portCode;
    private String portLongitude;
    private String portLatitude;
    private String portLogo;
    private String obs;

}
