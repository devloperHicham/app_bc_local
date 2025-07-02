package com.schedulerates.comparison.client;

import com.schedulerates.comparison.config.FeignClientConfig;
import com.schedulerates.comparison.model.common.dto.client.ApiResponse;
import com.schedulerates.comparison.model.common.dto.client.CompanyDto;
import com.schedulerates.comparison.model.common.dto.client.ContainerDto;
import com.schedulerates.comparison.model.common.dto.client.GargoDto;
import com.schedulerates.comparison.model.common.dto.client.PortDto;
import com.schedulerates.comparison.model.common.dto.client.TransportationDto;

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

    /**
     * Retrieves a Transportation by its ID.
     *
     * @param id The ID of the Transportation to retrieve.
     * @return The Transportation object corresponding to the given ID.
     */
    @GetMapping("/transportations/{id}")
    ApiResponse<TransportationDto> gettransportationById(@PathVariable String id,
            @RequestHeader("Authorization") String authHeader);

    /**
     * Retrieves a Gargo by its ID.
     *
     * @param id The ID of the Gargo to retrieve.
     * @return The Gargo object corresponding to the given ID.
     */
    @GetMapping("/gargos/{id}")
    ApiResponse<GargoDto> getGargoById(@PathVariable String id, @RequestHeader("Authorization") String authHeader);

    /**
     * Retrieves a Container by its ID.
     *
     * @param id The ID of the Container to retrieve.
     * @return The Container object corresponding to the given ID.
     */
    @GetMapping("/containers/{id}")
    ApiResponse<ContainerDto> getContainerById(@PathVariable String id,
            @RequestHeader("Authorization") String authHeader);
}