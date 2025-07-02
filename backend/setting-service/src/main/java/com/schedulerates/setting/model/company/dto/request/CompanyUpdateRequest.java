package com.schedulerates.setting.model.company.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a request object for updating an existing company as
 * {@link CompanyUpdateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUpdateRequest {

    @NotBlank(message = "Company name can't be blank.")
    @Size(max = 100, message = "Company name can't exceed 100 characters.")
    private String companyName;

    private MultipartFile companyLogo;

    private String obs;

}