package com.schedulerates.setting.controller;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.common.dto.response.CustomResponse;
import com.schedulerates.setting.model.gargo.Gargo;
import com.schedulerates.setting.model.gargo.dto.request.GargoCreateRequest;
import com.schedulerates.setting.model.gargo.dto.request.GargoPagingRequest;
import com.schedulerates.setting.model.gargo.dto.request.GargoUpdateRequest;
import com.schedulerates.setting.model.gargo.dto.response.GargoResponse;
import com.schedulerates.setting.model.gargo.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.setting.model.gargo.mapper.GargoToGargoResponseMapper;
import com.schedulerates.setting.service.gargo.GargoCreateService;
import com.schedulerates.setting.service.gargo.GargoDeleteService;
import com.schedulerates.setting.service.gargo.GargoReadService;
import com.schedulerates.setting.service.gargo.GargoUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link FaqController} for managing FAQs.
 * Provides endpoints to create, read, update, and delete FAQs.
 */
@RestController
@RequestMapping("/api/v1/settings/gargos")
@RequiredArgsConstructor
@Validated
public class GargoController {

    private final GargoCreateService gargoCreateService;
    private final GargoReadService gargoReadService;
    private final GargoUpdateService gargoUpdateService;
    private final GargoDeleteService gargoDeleteService;

    private final GargoToGargoResponseMapper gargoToGargoResponseMapper = GargoToGargoResponseMapper.initialize();

    private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
            .initialize();

    /**
     * Creates a new Gargo.
     *
     * @param gargoCreateRequest the request payload containing Gargo details
     * @return a {@link CustomResponse} containing the ID of the created Gargo
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<String> createGargo(@RequestBody @Valid final GargoCreateRequest gargoCreateRequest) {

        final Gargo createdGargo = gargoCreateService
                .createGargo(gargoCreateRequest);

        return CustomResponse.successOf(createdGargo.getId());
    }

    /**
     * Retrieves a Gargo by its ID.
     *
     * @param gargoId the ID of the Gargo to retrieve
     * @return a {@link CustomResponse} containing the Gargo details
     */
    @GetMapping("/{gargoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<GargoResponse> getGargoById(@PathVariable @UUID final String gargoId) {

        final Gargo gargo = gargoReadService.getGargoById(gargoId);

        final GargoResponse gargoResponse = gargoToGargoResponseMapper.map(gargo);

        return CustomResponse.successOf(gargoResponse);

    }

    /**
     * Retrieves a list of all Gargos.
     *
     * @return a {@link CustomResponse} containing the list of Gargo details
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<List<GargoResponse>> getAllGargos() {

        List<Gargo> gargos = gargoReadService.getAllGargos();

        List<GargoResponse> responses = gargos.stream()
                .map(gargoToGargoResponseMapper::map)
                .toList();

        return CustomResponse.successOf(responses);
    }

    /**
     * Retrieves a paginated list of Gargos based on the paging request.
     *
     * @param gargoPagingRequest the request payload containing paging information
     * @return a {@link CustomResponse} containing the paginated list of Gargos
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<CustomPagingResponse<GargoResponse>> getGargos(
            @RequestBody @Valid final GargoPagingRequest gargoPagingRequest) {

        final CustomPage<Gargo> gargoPage = gargoReadService.getGargos(gargoPagingRequest);

        final CustomPagingResponse<GargoResponse> gargoPagingResponse = customPageToCustomPagingResponseMapper
                .toPagingResponse(gargoPage);

        return CustomResponse.successOf(gargoPagingResponse);

    }

    /**
     * Updates an existing Gargo by its ID.
     *
     * @param gargoUpdateRequest the request payload containing updated Gargo
     *                           details
     * @param gargoId            the ID of the Gargo to update
     * @return a {@link CustomResponse} containing the updated Gargo details
     */
    @PutMapping("/{gargoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<GargoResponse> updatedGargoById(
            @RequestBody @Valid final GargoUpdateRequest gargoUpdateRequest,
            @PathVariable @UUID final String gargoId) {

        final Gargo updatedGargo = gargoUpdateService.updateGargoById(gargoId, gargoUpdateRequest);

        final GargoResponse gargoResponse = gargoToGargoResponseMapper.map(updatedGargo);

        return CustomResponse.successOf(gargoResponse);
    }

    /**
     * Deletes a Gargo by its ID.
     *
     * @param gargoId the ID of the Gargo to delete
     * @return a {@link CustomResponse} indicating successful deletion
     */
    @DeleteMapping("/{gargoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<Void> deleteGargoById(@PathVariable @UUID final String gargoId) {

        gargoDeleteService.deleteGargoById(gargoId);
        return CustomResponse.SUCCESS;
    }

}
