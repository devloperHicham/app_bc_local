package com.schedulerates.setting.model.port.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a request object for creating a new port as
 * {@link PortCreateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortCreateRequest {

    @NotBlank(message = "Port name can't be blank.")
    @Size(max = 100, message = "Port name can't exceed 100 characters.")
    private String portName;

    @NotBlank(message = "county name can't be blank.")
    @Size(max = 100, message = "cpuntry name can't exceed 100 characters.")
    private String countryName;

    @NotBlank(message = "county name abbreviation can't be blank.")
    @Size(max = 100, message = "cpuntry name can't exceed 100 characters.")
    private String countryNameAbbreviation;

    @NotBlank(message = "code can't be blank.")
    @Size(max = 100, message = "Port code can't exceed 100 characters.")
    private String portCode;

    @NotNull(message = "Port Longitude is required.")
    private Double portLongitude;

    @NotNull(message = "Port Latitude is required.")
    private Double portLatitude;

    private MultipartFile portLogo;

    private String obs;

}
