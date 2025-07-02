package com.schedulerates.setting.model.gargo.dto.response;

import lombok.*;

/**
 * Represents a response object containing gargo details as {@link gargoResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GargoResponse {

    private String id;
    private String gargoName;
    private String obs;

}