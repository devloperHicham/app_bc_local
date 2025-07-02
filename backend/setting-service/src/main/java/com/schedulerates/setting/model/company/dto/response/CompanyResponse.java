package com.schedulerates.setting.model.company.dto.response;

import lombok.*;

/**
 * Represents a response object containing company details as {@link CompanyResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private String id;
    private String companyName;
    private String companyLogo;
    private String obs;

}