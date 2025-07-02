package com.schedulerates.setting.model.faq.dto.response;

import lombok.*;

/**
 * Represents a response object containing FAQ details as {@link FaqResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqResponse {

    private String id;
    private String fullName;
    private String typeFaq;
    private String active;
    private String obs;

}