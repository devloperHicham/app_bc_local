package com.schedulerates.setting.model.transportation.dto.response;

import lombok.*;

/**
 * Represents a response object containing Transportation details as {@link TransportationResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportationResponse {

    private String id;
    private String transportationName;
    private String obs;

}