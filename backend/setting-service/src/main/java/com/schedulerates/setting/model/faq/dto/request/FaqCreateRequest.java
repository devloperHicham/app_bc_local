package com.schedulerates.setting.model.faq.dto.request;

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
public class FaqCreateRequest {

        @Size(min = 1, message = "FAQ full name can't be blank.")
        private String fullName;

        @Size(min = 1, message = "FAQ type can't be blank.")
        private String typeFaq;

        @Size(min = 5, message = "FAQ observation can't be blank.")
        private String obs;

}