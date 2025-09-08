package com.schedulerates.comparison.model.common.dto.client;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String id;
    private String companyName;
    private String companyLogo;
}
