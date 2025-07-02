package com.schedulerates.setting.model.gargo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a request object for creating a new gargo as
 * {@link gargoCreateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GargoCreateRequest {

        @Size(min = 1, message = "Gargo full name can't be blank.")
        private String gargoName;

        private String obs;

}
