package com.schedulerates.setting.model.transportation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a request object for creating a new FAQ as
 * {@link FaqCreateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportationCreateRequest {

        @Size(min = 1, message = "Transportation  name can't be blank.")
        private String transportationName;

        private String obs;

}
