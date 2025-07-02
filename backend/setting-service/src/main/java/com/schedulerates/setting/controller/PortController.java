package com.schedulerates.setting.controller;

import org.springframework.core.io.Resource;
import com.schedulerates.setting.exception.StorageException;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.common.dto.response.CustomResponse;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.request.PortCreateRequest;
import com.schedulerates.setting.model.port.dto.request.PortPagingRequest;
import com.schedulerates.setting.model.port.dto.request.PortUpdateRequest;
import com.schedulerates.setting.model.port.dto.response.PortResponse;
import com.schedulerates.setting.model.port.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.setting.model.port.mapper.PortToPortResponseMapper;
import com.schedulerates.setting.service.port.PortCreateService;
import com.schedulerates.setting.service.port.PortDeleteService;
import com.schedulerates.setting.service.port.PortReadService;
import com.schedulerates.setting.service.port.PortUpdateService;
import com.schedulerates.setting.storage.StorageImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * REST controller named {@link PortController} for managing Companies.
 * Provides endpoints to create, read, update, and delete Companies.
 */
@RestController
@RequestMapping("/api/v1/settings/ports")
@RequiredArgsConstructor
@Validated
public class PortController {

    private final PortCreateService portCreateService;
    private final PortReadService portReadService;
    private final PortUpdateService portUpdateService;
    private final PortDeleteService portDeleteService;
    private final StorageImageService storageService;

    private final PortToPortResponseMapper portToPortResponseMapper = PortToPortResponseMapper
            .initialize();

    private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
            .initialize();

    /**
     * Creates a new port.
     *
     * @param portCreateRequest the request payload containing Port details
     * @return a {@link CustomResponse} containing the ID of the created Port
     */
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<String> createPort(@ModelAttribute PortCreateRequest portCreateRequest) {

        final Port createdPort = portCreateService.createPort(portCreateRequest, portCreateRequest.getPortLogo());

        return CustomResponse.successOf(createdPort.getId());
    }

    /**
     * Retrieves a Port by its ID.
     *
     * @param portId the ID of the Port to retrieve
     * @return a {@link CustomResponse} containing the Port details
     */
    @GetMapping("/{portId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<PortResponse> getPortById(@PathVariable @UUID final String portId) {

        final Port port = portReadService.getPortById(portId);

        final PortResponse portResponse = portToPortResponseMapper.map(port);

        return CustomResponse.successOf(portResponse);

    }

    /**
     * Retrieves all ports.
     *
     * @return a {@link CustomResponse} containing all ports
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<List<PortResponse>> getAllPorts() {

        List<Port> companies = portReadService.getAllPorts();

        List<PortResponse> responses = companies.stream()
                .map(portToPortResponseMapper::map)
                .toList();

        return CustomResponse.successOf(responses);
    }

    /**
     * Retrieves a paginated list of Ports based on the paging request.
     *
     * @param portPagingRequest the request payload containing paging information
     * @return a {@link CustomResponse} containing the paginated list of Ports
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<CustomPagingResponse<PortResponse>> getPorts(
            @RequestBody @Valid final PortPagingRequest portPagingRequest) {

        final CustomPage<Port> portPage = portReadService.getPorts(portPagingRequest);

        final CustomPagingResponse<PortResponse> portPagingResponse = customPageToCustomPagingResponseMapper
                .toPagingResponse(portPage);

        return CustomResponse.successOf(portPagingResponse);

    }

    /**
     * Serves port logo images
     * 
     * @param filename The name of the image file to serve
     * @return The image file as a resource
     */
    @GetMapping(value = "/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Resource file = storageService.load(filename);
            String contentType = determineContentType(filename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + file.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(file);
        } catch (StorageException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String determineContentType(String filename) {
        // Add logic to determine content type based on file extension
        if (filename.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
            return "image/jpeg";
        }
        // Default to octet-stream if unknown
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    /**
     * Updates an existing Port by its ID.
     *
     * @param portUpdateRequest the request payload containing updated Port details
     * @param portId            the ID of the Port to update
     * @return a {@link CustomResponse} containing the updated Port details
     */
    @PutMapping(value = "/{portId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<String> updatePortById(@PathVariable @UUID final String portId,
            @ModelAttribute PortUpdateRequest portUpdateRequest) {

        final Port updatedPort = portUpdateService.updatePortById(portId, portUpdateRequest,
                portUpdateRequest.getPortLogo());
        return CustomResponse.successOf(updatedPort.getId());
    }

    /**
     * Deletes a Port by its ID.
     *
     * @param portId the ID of the Port to delete
     * @return a {@link CustomResponse} indicating successful deletion
     */
    @DeleteMapping("/{portId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<Void> deletePortById(@PathVariable @UUID final String portId) {

        portDeleteService.deletePortById(portId);
        return CustomResponse.SUCCESS;
    }

}
