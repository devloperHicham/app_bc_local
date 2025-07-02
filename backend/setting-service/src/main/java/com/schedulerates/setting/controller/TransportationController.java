package com.schedulerates.setting.controller;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.common.dto.response.CustomResponse;
import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.request.TransportationCreateRequest;
import com.schedulerates.setting.model.transportation.dto.request.TransportationPagingRequest;
import com.schedulerates.setting.model.transportation.dto.request.TransportationUpdateRequest;
import com.schedulerates.setting.model.transportation.dto.response.TransportationResponse;
import com.schedulerates.setting.model.transportation.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.setting.model.transportation.mapper.TransportationToTransportationResponseMapper;
import com.schedulerates.setting.service.transportation.TransportationCreateService;
import com.schedulerates.setting.service.transportation.TransportationDeleteService;
import com.schedulerates.setting.service.transportation.TransportationReadService;
import com.schedulerates.setting.service.transportation.TransportationUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link TransportationController} for managing
 * Transportations.
 * Provides endpoints to create, read, update, and delete Transportations.
 */
@RestController
@RequestMapping("/api/v1/settings/transportations")
@RequiredArgsConstructor
@Validated
public class TransportationController {

    private final TransportationCreateService transportationCreateService;
    private final TransportationReadService transportationReadService;
    private final TransportationUpdateService transportationUpdateService;
    private final TransportationDeleteService transportationDeleteService;

    private final TransportationToTransportationResponseMapper transportationToTransportationResponseMapper = TransportationToTransportationResponseMapper
            .initialize();

    private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
            .initialize();

    /**
     * Creates a new Transportation.
     *
     * @param transportationCreateRequest the request payload containing
     *                                    Transportation details
     * @return a {@link CustomResponse} containing the ID of the created
     *         Transportation
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<String> createTransportation(
            @RequestBody @Valid final TransportationCreateRequest transportationCreateRequest) {

        final Transportation createdTransportation = transportationCreateService
                .createTransportation(transportationCreateRequest);

        return CustomResponse.successOf(createdTransportation.getId());
    }

    /**
     * Retrieves a Transportation by its ID.
     *
     * @param transportationId the ID of the Transportation to retrieve
     * @return a {@link CustomResponse} containing the Transportation details
     */
    @GetMapping("/{transportationId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<TransportationResponse> getTransportationById(
            @PathVariable @UUID final String transportationId) {

        final Transportation transportation = transportationReadService.getTransportationById(transportationId);

        final TransportationResponse transportationResponse = transportationToTransportationResponseMapper
                .map(transportation);

        return CustomResponse.successOf(transportationResponse);

    }

    /**
     * Retrieves all Transportations.
     *
     * @return a {@link CustomResponse} containing the list of all Transportations
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<List<TransportationResponse>> getAllTransportations() {

        List<Transportation> transportations = transportationReadService.getAllTransportations();

        List<TransportationResponse> responses = transportations.stream()
                .map(transportationToTransportationResponseMapper::map)
                .toList();

        return CustomResponse.successOf(responses);
    }

    /**
     * Retrieves a paginated list of Transportations based on the paging request.
     *
     * @param transportationPagingRequest the request payload containing paging
     *                                    information
     * @return a {@link CustomResponse} containing the paginated list of
     *         Transportations
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<CustomPagingResponse<TransportationResponse>> getTransportations(
            @RequestBody @Valid final TransportationPagingRequest transportationPagingRequest) {

        final CustomPage<Transportation> transportationPage = transportationReadService
                .getTransportations(transportationPagingRequest);

        final CustomPagingResponse<TransportationResponse> transportationPagingResponse = customPageToCustomPagingResponseMapper
                .toPagingResponse(transportationPage);

        return CustomResponse.successOf(transportationPagingResponse);

    }

    /**
     * Updates an existing Transportation by its ID.
     *
     * @param transportationUpdateRequest the request payload containing updated
     *                                    Transportation details
     * @param transportationId            the ID of the Transportation to update
     * @return a {@link CustomResponse} containing the updated Transportation
     *         details
     */
    @PutMapping("/{transportationId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<TransportationResponse> updatedTransportationById(
            @RequestBody @Valid final TransportationUpdateRequest transportationUpdateRequest,
            @PathVariable @UUID final String transportationId) {

        final Transportation updatedTransportation = transportationUpdateService
                .updateTransportationById(transportationId, transportationUpdateRequest);

        final TransportationResponse transportationResponse = transportationToTransportationResponseMapper
                .map(updatedTransportation);

        return CustomResponse.successOf(transportationResponse);
    }

    /**
     * Deletes a Transportation by its ID.
     *
     * @param transportationId the ID of the Transportation to delete
     * @return a {@link CustomResponse} indicating successful deletion
     */
    @DeleteMapping("/{transportationId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<Void> deleteTransportationById(@PathVariable @UUID final String transportationId) {

        transportationDeleteService.deleteTransportationById(transportationId);
        return CustomResponse.SUCCESS;
    }

}
