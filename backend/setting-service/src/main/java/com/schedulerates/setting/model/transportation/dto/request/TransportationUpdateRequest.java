package com.schedulerates.setting.model.transportation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a request object for updating an existing Transportation as
 * {@link TransportationUpdateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportationUpdateRequest {

    @Size(min = 1, message = "Transportation full name can't be blank.")
    private String transportationName;

    private String obs;

}