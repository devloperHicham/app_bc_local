package com.schedulerates.setting.controller;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.common.dto.response.CustomResponse;
import com.schedulerates.setting.model.container.Container;
import com.schedulerates.setting.model.container.dto.request.ContainerCreateRequest;
import com.schedulerates.setting.model.container.dto.request.ContainerPagingRequest;
import com.schedulerates.setting.model.container.dto.request.ContainerUpdateRequest;
import com.schedulerates.setting.model.container.dto.response.ContainerResponse;
import com.schedulerates.setting.model.container.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.setting.model.container.mapper.ContainerToContainerResponseMapper;
import com.schedulerates.setting.service.container.ContainerCreateService;
import com.schedulerates.setting.service.container.ContainerDeleteService;
import com.schedulerates.setting.service.container.ContainerReadService;
import com.schedulerates.setting.service.container.ContainerUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link ContainerController} for managing Containers.
 * Provides endpoints to create, read, update, and delete Containers.
 */
@RestController
@RequestMapping("/api/v1/settings/containers")
@RequiredArgsConstructor
@Validated
public class ContainerController {

        private final ContainerCreateService containerCreateService;
        private final ContainerReadService containerReadService;
        private final ContainerUpdateService containerUpdateService;
        private final ContainerDeleteService containerDeleteService;

        private final ContainerToContainerResponseMapper containerToContainerResponseMapper = ContainerToContainerResponseMapper
                        .initialize();

        private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
                        .initialize();

        /**
         * Creates a new container.
         *
         * @param containerCreateRequest the request payload containing container
         *                               details
         * @return a {@link CustomResponse} containing the ID of the created container
         */
        @PostMapping("/create")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<String> createContainer(
                        @RequestBody @Valid final ContainerCreateRequest containerCreateRequest) {

                final Container createdContainer = containerCreateService
                                .createContainer(containerCreateRequest);

                return CustomResponse.successOf(createdContainer.getId());
        }

        /**
         * Retrieves a container by its ID.
         *
         * @param containerId the ID of the container to retrieve
         * @return a {@link CustomResponse} containing the container details
         */
        @GetMapping("/{containerId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<ContainerResponse> getContainerById(@PathVariable @UUID final String containerId) {

                final Container container = containerReadService.getContainerById(containerId);

                final ContainerResponse containerResponse = containerToContainerResponseMapper.map(container);

                return CustomResponse.successOf(containerResponse);

        }

        /**
         * Retrieves a list of all containers.
         *
         * @return a {@link CustomResponse} containing the list of container details
         */
        @GetMapping("/all")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<List<ContainerResponse>> getAllContainers() {

                List<Container> containers = containerReadService.getAllContainers();

                List<ContainerResponse> responses = containers.stream()
                                .map(containerToContainerResponseMapper::map)
                                .toList();

                return CustomResponse.successOf(responses);
        }

        /**
         * Retrieves a paginated list of containers based on the paging request.
         *
         * @param containerPagingRequest the request payload containing paging
         *                               information
         * @return a {@link CustomResponse} containing the paginated list of containers
         */
        @PostMapping
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<CustomPagingResponse<ContainerResponse>> getContainers(
                        @RequestBody @Valid final ContainerPagingRequest containerPagingRequest) {

                final CustomPage<Container> containerPage = containerReadService.getContainers(containerPagingRequest);

                final CustomPagingResponse<ContainerResponse> containerPagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(containerPage);

                return CustomResponse.successOf(containerPagingResponse);

        }

        /**
         * Updates an existing container by its ID.
         *
         * @param containerUpdateRequest the request payload containing updated
         *                               container details
         * @param containerId            the ID of the container to update
         * @return a {@link CustomResponse} containing the updated container details
         */
        @PutMapping("/{containerId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<ContainerResponse> updatedContainerById(
                        @RequestBody @Valid final ContainerUpdateRequest containerUpdateRequest,
                        @PathVariable @UUID final String containerId) {

                final Container updatedContainer = containerUpdateService.updateContainerById(containerId,
                                containerUpdateRequest);

                final ContainerResponse containerResponse = containerToContainerResponseMapper.map(updatedContainer);

                return CustomResponse.successOf(containerResponse);
        }

        /**
         * Deletes a container by its ID.
         *
         * @param containerId the ID of the container to delete
         * @return a {@link CustomResponse} indicating successful deletion
         */
        @DeleteMapping("/{containerId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<Void> deleteContainerById(@PathVariable @UUID final String containerId) {

                containerDeleteService.deleteContainerById(containerId);
                return CustomResponse.SUCCESS;
        }

}