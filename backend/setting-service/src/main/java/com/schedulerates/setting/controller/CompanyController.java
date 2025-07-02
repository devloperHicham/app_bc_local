package com.schedulerates.setting.controller;

import org.springframework.core.io.Resource;
import com.schedulerates.setting.exception.StorageException;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.common.dto.response.CustomResponse;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.request.CompanyCreateRequest;
import com.schedulerates.setting.model.company.dto.request.CompanyPagingRequest;
import com.schedulerates.setting.model.company.dto.request.CompanyUpdateRequest;
import com.schedulerates.setting.model.company.dto.response.CompanyResponse;
import com.schedulerates.setting.model.company.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.setting.model.company.mapper.CompanyToCompanyResponseMapper;
import com.schedulerates.setting.service.company.CompanyCreateService;
import com.schedulerates.setting.service.company.CompanyDeleteService;
import com.schedulerates.setting.service.company.CompanyReadService;
import com.schedulerates.setting.service.company.CompanyUpdateService;
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
 * REST controller named {@link CompanyController} for managing Companies.
 * Provides endpoints to create, read, update, and delete Companies.
 */
@RestController
@RequestMapping("/api/v1/settings/companies")
@RequiredArgsConstructor
@Validated
public class CompanyController {

        private final CompanyCreateService companyCreateService;
        private final CompanyReadService companyReadService;
        private final CompanyUpdateService companyUpdateService;
        private final CompanyDeleteService companyDeleteService;
        private final StorageImageService storageService;

        private final CompanyToCompanyResponseMapper companyToCompanyResponseMapper = CompanyToCompanyResponseMapper
                        .initialize();

        private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
                        .initialize();

        /**
         * Creates a new Company.
         *
         * @param companyCreateRequest the request payload containing Company details
         * @return a {@link CustomResponse} containing the ID of the created Company
         */
        @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<String> createCompany(@ModelAttribute CompanyCreateRequest companyCreateRequest) {

                final Company createdCompany = companyCreateService.createCompany(
                                companyCreateRequest, companyCreateRequest.getCompanyLogo());

                return CustomResponse.successOf(createdCompany.getId());
        }

        /**
         * Retrieves a Company by its ID.
         *
         * @param companyId the ID of the Company to retrieve
         * @return a {@link CustomResponse} containing the Company details
         */
        @GetMapping("/{companyId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<CompanyResponse> getCompanyById(@PathVariable @UUID final String companyId) {

                final Company company = companyReadService.getCompanyById(companyId);

                final CompanyResponse companyResponse = companyToCompanyResponseMapper.map(company);

                return CustomResponse.successOf(companyResponse);

        }

        /**
         * Retrieves all Companies.
         *
         * @return a {@link CustomResponse} containing the list of all Companies
         */
        @GetMapping("/all")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<List<CompanyResponse>> getAllCompanies() {

                List<Company> companies = companyReadService.getAllCompanies();

                List<CompanyResponse> responses = companies.stream()
                                .map(companyToCompanyResponseMapper::map)
                                .toList();

                return CustomResponse.successOf(responses);
        }

        /**
         * Retrieves a paginated list of Companies based on the paging request.
         *
         * @param companyPagingRequest the request payload containing paging information
         * @return a {@link CustomResponse} containing the paginated list of Companies
         */
        @PostMapping
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<CustomPagingResponse<CompanyResponse>> getCompanies(
                        @RequestBody @Valid final CompanyPagingRequest companyPagingRequest) {

                final CustomPage<Company> companyPage = companyReadService.getCompanies(companyPagingRequest);

                final CustomPagingResponse<CompanyResponse> companyPagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(companyPage);

                return CustomResponse.successOf(companyPagingResponse);

        }

        /**
         * Serves company logo images
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
         * Updates an existing Company by its ID.
         *
         * @param companyUpdateRequest the request payload containing updated Company
         *                             details
         * @param companyId            the ID of the Company to update
         * @return a {@link CustomResponse} containing the updated Company details
         */
        @PutMapping(value = "/{companyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<String> updateCompanyById(@PathVariable @UUID final String companyId,
                        @ModelAttribute CompanyUpdateRequest companyUpdateRequest) {

                final Company updatedCompany = companyUpdateService.updateCompanyById(companyId,
                                companyUpdateRequest, companyUpdateRequest.getCompanyLogo());
                return CustomResponse.successOf(updatedCompany.getId());
        }

        /**
         * Deletes a Company by its ID.
         *
         * @param companyId the ID of the Company to delete
         * @return a {@link CustomResponse} indicating successful deletion
         */
        @DeleteMapping("/{companyId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<Void> deleteCompanyById(@PathVariable @UUID final String companyId) {

                companyDeleteService.deleteCompanyById(companyId);
                return CustomResponse.SUCCESS;
        }

}