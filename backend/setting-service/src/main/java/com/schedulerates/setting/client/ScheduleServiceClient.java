package com.schedulerates.setting.client;

import com.schedulerates.setting.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Feign client interface named {@link UserServiceClient} for interacting with
 * the User Service.
 * Provides methods to validate tokens and retrieve authentication information.
 */
@FeignClient(name = "schedule-service", path = "/api/v1/schedules", configuration = FeignClientConfig.class)
public interface ScheduleServiceClient {

    /**
     * Retrieves a Port by its ID.
     *
     * @param id The ID of the Port to retrieve.
     * @return The Port object corresponding to the given ID.
     */
    @GetMapping("/ports/{id}")
    boolean existsByPortById(@PathVariable String id, @RequestHeader("Authorization") String authHeader);

    /**
     * Retrieves a Company by its ID.
     *
     * @param id The ID of the Company to retrieve.
     * @return The Company object corresponding to the given ID.
     */
    @GetMapping("/companies/{id}")
    boolean existsByCompanyById(@PathVariable String id, @RequestHeader("Authorization") String authHeader);

    /**
     * Retrieves a Transportation by its ID.
     *
     * @param id The ID of the Transportation to retrieve.
     * @return The Transportation object corresponding to the given ID.
     */
    @GetMapping("/transportations/{id}")
    boolean existsByTransportationById(@PathVariable String id,
            @RequestHeader("Authorization") String authHeader);

    /**
     * Retrieves a Gargo by its ID.
     *
     * @param id The ID of the Gargo to retrieve.
     * @return The Gargo object corresponding to the given ID.
     */
    @GetMapping("/gargos/{id}")
    boolean existsByGargoById(@PathVariable String id, @RequestHeader("Authorization") String authHeader);

    /**
     * Retrieves a Container by its ID.
     *
     * @param id The ID of the Container to retrieve.
     * @return The Container object corresponding to the given ID.
     */
    @GetMapping("/containers/{id}")
    boolean existsByContainerById(@PathVariable String id,
            @RequestHeader("Authorization") String authHeader);
}