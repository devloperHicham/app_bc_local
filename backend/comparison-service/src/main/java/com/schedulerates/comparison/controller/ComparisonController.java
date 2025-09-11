package com.schedulerates.comparison.controller;

import com.schedulerates.comparison.model.common.CustomPage;
import com.schedulerates.comparison.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.comparison.model.common.dto.response.CustomResponse;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonCreateRequest;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonPagingHistoriqueRequest;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonPagingRequest;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonClientPagingRequest;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonUpdateRequest;
import com.schedulerates.comparison.model.comparison.dto.response.ComparisonResponse;
import com.schedulerates.comparison.model.comparison.dto.response.DashboardResponse;
import com.schedulerates.comparison.model.comparison.dto.response.WeeklyComparisonByCompaniesData;
import com.schedulerates.comparison.model.comparison.dto.response.WeeklyComparisonData;
import com.schedulerates.comparison.model.comparison.dto.response.DailyComparisonByUsersData;
import com.schedulerates.comparison.model.comparison.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.model.comparison.mapper.ComparisonToComparisonResponseMapper;
import com.schedulerates.comparison.service.comparison.ComparisonCreateService;
import com.schedulerates.comparison.service.comparison.ComparisonDashboardService;
import com.schedulerates.comparison.service.comparison.ComparisonDeleteService;
import com.schedulerates.comparison.service.comparison.ComparisonHistoriqueService;
import com.schedulerates.comparison.service.comparison.ComparisonReadService;
import com.schedulerates.comparison.service.comparison.ComparisonUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link ScheduleController} for managing Schedules.
 * Provides endpoints to create, read, update, and delete Schedules.
 */
@RestController
@RequestMapping("/api/v1/comparisons")
@RequiredArgsConstructor
@Validated
public class ComparisonController {

        private final ComparisonCreateService comparisonCreateService;
        private final ComparisonReadService comparisonReadService;
        private final ComparisonUpdateService comparisonUpdateService;
        private final ComparisonDeleteService comparisonDeleteService;
        private final ComparisonRepository comparisonRepository;
        private final ComparisonDashboardService comparisonDashboardService;
        private final ComparisonHistoriqueService comparisonHistoriqueService;

        private final ComparisonToComparisonResponseMapper comparisonToComparisonResponseMapper = ComparisonToComparisonResponseMapper
                        .initialize();

        private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
                        .initialize();

        /**
         * Creates a new comparison.
         *
         * @param comparisonCreateRequest the request payload containing Transportation
         *                                details
         * @return a {@link CustomResponse} containing the ID of the created
         *         Transportation
         */
        @PostMapping("/create")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<List<String>> createComparison(
                        @RequestBody @Valid final ComparisonCreateRequest comparisonCreateRequest) {

                List<Comparison> createdComparisons = comparisonCreateService
                                .createComparison(comparisonCreateRequest);

                // Extract IDs of all created comparisons
                List<String> createdIds = createdComparisons.stream()
                                .map(Comparison::getId)
                                .toList();

                return CustomResponse.successOf(createdIds);
        }

        /**
         * Retrieves a comparison by its ID.
         *
         * @param comparisonId the ID of the comparison to retrieve
         * @return a {@link CustomResponse} containing the comparison details
         */
        @GetMapping("/{comparisonId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<ComparisonResponse> getComparisonById(@PathVariable @UUID final String comparisonId) {

                final Comparison comparison = comparisonReadService.getComparisonById(comparisonId);

                final ComparisonResponse comparisonResponse = comparisonToComparisonResponseMapper.map(comparison);

                return CustomResponse.successOf(comparisonResponse);

        }

        /**
         * Retrieves a paginated list of comparisons based on the paging request.
         *
         * @param comparisonPagingRequest the request payload containing paging
         *                                information
         * @return a {@link CustomResponse} containing the paginated list of comparisons
         */
        @PostMapping
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<CustomPagingResponse<ComparisonResponse>> getComparisons(
                        @RequestBody @Valid final ComparisonPagingRequest comparisonPagingRequest) {

                final CustomPage<Comparison> comparisonPage = comparisonReadService
                                .getComparisons(comparisonPagingRequest);

                final CustomPagingResponse<ComparisonResponse> comparisonPagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(comparisonPage);

                return CustomResponse.successOf(comparisonPagingResponse);

        }

        /**
         * Retrieves a paginated list of Comparisons based on the paging request.
         *
         * @param comparisonPagingRequest the request payload containing paging
         *                                information
         * @return a {@link CustomResponse} containing the paginated list of Comparisons
         */
        @PostMapping("/historiques")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public CustomResponse<CustomPagingResponse<ComparisonResponse>> getHistoriques(
                        @RequestBody @Valid final ComparisonPagingHistoriqueRequest comparisonPagingHistoriqueRequest) {

                final CustomPage<Comparison> comparisonPage = comparisonHistoriqueService
                                .getHistoriques(comparisonPagingHistoriqueRequest);

                final CustomPagingResponse<ComparisonResponse> comparisonPagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(comparisonPage);

                return CustomResponse.successOf(comparisonPagingResponse);

        }

        /**
         * Updates an existing comparison by its ID.
         *
         * @param comparisonUpdateRequest the request payload containing updated
         *                                comparison details
         * @param comparisonId            the ID of the comparison to update
         * @return a {@link CustomResponse} containing the updated comparison details
         */
        @PutMapping("/{comparisonId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<ComparisonResponse> updateComparisonById(
                        @RequestBody @Valid final ComparisonUpdateRequest comparisonUpdateRequest,
                        @PathVariable @UUID final String comparisonId) {

                final Comparison updatedComparison = comparisonUpdateService.updateComparisonById(comparisonId,
                                comparisonUpdateRequest);

                final ComparisonResponse comparisonResponse = comparisonToComparisonResponseMapper
                                .map(updatedComparison);

                return CustomResponse.successOf(comparisonResponse);
        }

        /**
         * Deletes a comparison by its ID.
         *
         * @param comparisonId the ID of the comparison to delete
         * @return a {@link CustomResponse} indicating successful deletion
         */
        @DeleteMapping("/{comparisonId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<Void> deleteComparisonById(@PathVariable @UUID final String comparisonId) {

                comparisonDeleteService.deleteComparisonById(comparisonId);
                return CustomResponse.SUCCESS;
        }

        @DeleteMapping("/all")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<Void> deleteComparisonsByIds(@RequestBody List<@UUID String> comparisonIds) {
                comparisonDeleteService.deleteComparisonsByIds(comparisonIds);
                return CustomResponse.SUCCESS;
        }

        /**
         * Checks if a Schedule exists with either portFromId or portToId matching
         * the given ID and active is set to '1'.
         *
         * @param id the ID to check
         * @return true if a Schedule exists with the given ID, false otherwise
         */
        @GetMapping("/ports/{id}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public boolean existsByPortId(@PathVariable String id) {
                return comparisonRepository.existsByPortFromIdAndActive(id, "1")
                                || comparisonRepository.existsByPortToIdAndActive(id, "1");
        }

        @GetMapping("/companies/{id}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public boolean existsByCompanyId(@PathVariable String id) {
                return comparisonRepository.existsByCompanyIdAndActive(id, "1");
        }

        @GetMapping("/containers/{id}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public boolean existsByContainerId(@PathVariable String id) {
                return comparisonRepository.existsByContainerIdAndActive(id, "1");
        }

        @GetMapping("/gargos/{id}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public boolean existsByGargoId(@PathVariable String id) {
                return comparisonRepository.existsByGargoIdAndActive(id, "1");
        }

        @GetMapping("/transportations/{id}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public boolean existsByTransportationId(@PathVariable String id) {
                return comparisonRepository.existsByTransportationIdAndActive(id, "1");
        }

        /**
         * Retrieves statistics for the current user, which are the number of Comparison
         * for today, number of Comparison for yesterday, and the score of the user
         * calculated by adding the number of Comparison for today and the number of
         * Comparison for yesterday. If the user is an admin, the statistics are
         * retrieved for all users.
         *
         * @return a {@link CustomResponse} containing the statistics
         */

        @GetMapping("/stats")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public DashboardResponse getComparisonStats() {
                return comparisonDashboardService.getComparisonStats();
        }

        @GetMapping("/graphics")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public List<WeeklyComparisonData> getWeeklyComparisonStats() {
                return comparisonDashboardService.getGraphicComparisons();
        }

        @GetMapping("/graphic-companies")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public List<WeeklyComparisonByCompaniesData> getWeeklyComparisonByCompaniesStats() {
                return comparisonDashboardService.getGraphicComparisonByCompanies();
        }

        @GetMapping("/graphic-users")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public List<DailyComparisonByUsersData> getDailyComparisonByUsersStats() {
                return comparisonDashboardService.getDailyComparisonByUsers();
        }

                /**
         * Retrieves a paginated list of comparisons based on the paging request.
         *
         * @param comparisonPagingRequest the request payload containing paging
         *                                information
         * @return a {@link CustomResponse} containing the paginated list of comparisons
         */
        @PostMapping("/clients")
        @PreAuthorize("hasAnyAuthority('CLIENT')")
        public CustomResponse<CustomPagingResponse<ComparisonResponse>> getComparisonClients(
                        @RequestBody @Valid final ComparisonClientPagingRequest comparisonClientPagingRequest) {

                final CustomPage<Comparison> comparisonPage = comparisonReadService
                                .getComparisonClients(comparisonClientPagingRequest);

                final CustomPagingResponse<ComparisonResponse> comparisonPagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(comparisonPage);

                return CustomResponse.successOf(comparisonPagingResponse);

        }
}
