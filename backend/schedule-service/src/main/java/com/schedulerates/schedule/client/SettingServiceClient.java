package com.schedulerates.schedule.client;

import com.schedulerates.schedule.config.FeignClientConfig;
import com.schedulerates.schedule.model.common.dto.client.ApiResponse;
import com.schedulerates.schedule.model.common.dto.client.CompanyDto;
import com.schedulerates.schedule.model.common.dto.client.PortDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Feign client interface named {@link UserServiceClient} for interacting with
 * the User Service.
 * Provides methods to validate tokens and retrieve authentication information.
 */
@FeignClient(name = "setting-service", path = "/api/v1/settings", configuration = FeignClientConfig.class)
public interface SettingServiceClient {

    /**
     * Retrieves a Port by its ID.
     *
     * @param id The ID of the Port to retrieve.
     * @return The Port object corresponding to the given ID.
     */
    @GetMapping("/ports/{id}")
    ApiResponse<PortDto> getPortById(@PathVariable String id, @RequestHeader("Authorization") String authHeader);
    /**
     * Retrieves a Company by its ID.
     *
     * @param id The ID of the Company to retrieve.
     * @return The Company object corresponding to the given ID.
     */
    @GetMapping("/companies/{id}")
    ApiResponse<CompanyDto> getCompanyById(@PathVariable String id, @RequestHeader("Authorization") String authHeader);
}