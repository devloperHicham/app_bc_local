package com.schedulerates.schedule.controller;

import com.schedulerates.schedule.model.common.CustomPage;
import com.schedulerates.schedule.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.schedule.model.common.dto.response.CustomResponse;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleCreateRequest;
import com.schedulerates.schedule.model.schedule.dto.request.SchedulePagingHistoriqueRequest;
import com.schedulerates.schedule.model.schedule.dto.request.SchedulePagingRequest;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleUpdateRequest;
import com.schedulerates.schedule.model.schedule.dto.response.DailyScheduleByUsersData;
import com.schedulerates.schedule.model.schedule.dto.response.DashboardResponse;
import com.schedulerates.schedule.model.schedule.dto.response.ScheduleResponse;
import com.schedulerates.schedule.model.schedule.dto.response.WeeklyScheduleByCompaniesData;
import com.schedulerates.schedule.model.schedule.dto.response.WeeklyScheduleData;
import com.schedulerates.schedule.model.schedule.mapper.CustomPageToCustomPagingResponseMapper;
import com.schedulerates.schedule.model.schedule.mapper.ScheduleToScheduleResponseMapper;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleCreateService;
import com.schedulerates.schedule.service.schedule.ScheduleDashboardService;
import com.schedulerates.schedule.service.schedule.ScheduleDeleteService;
import com.schedulerates.schedule.service.schedule.ScheduleHistoriqueService;
import com.schedulerates.schedule.service.schedule.ScheduleReadService;
import com.schedulerates.schedule.service.schedule.ScheduleUpdateService;
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
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Validated
public class ScheduleController {

        private final ScheduleCreateService scheduleCreateService;
        private final ScheduleReadService scheduleReadService;
        private final ScheduleUpdateService scheduleUpdateService;
        private final ScheduleDeleteService scheduleDeleteService;
        private final ScheduleHistoriqueService scheduleHistoriqueService;
        private final ScheduleDashboardService scheduleDashboardService;
        private final ScheduleRepository scheduleRepository;

        private final ScheduleToScheduleResponseMapper scheduleToScheduleResponseMapper = ScheduleToScheduleResponseMapper
                        .initialize();

        private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper = CustomPageToCustomPagingResponseMapper
                        .initialize();

        /**
         * Creates a new schedule.
         *
         * @param scheduleCreateRequest the request payload containing Transportation
         *                              details
         * @return a {@link CustomResponse} containing the ID of the created
         *         Transportation
         */
        @PostMapping("/create")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<List<String>> createSchedule(
                        @RequestBody @Valid final ScheduleCreateRequest scheduleCreateRequest) {

                List<Schedule> createdSchedules = scheduleCreateService
                                .createSchedule(scheduleCreateRequest);

                // Extract IDs of all created schedules
                List<String> createdIds = createdSchedules.stream()
                                .map(Schedule::getId)
                                .toList();

                return CustomResponse.successOf(createdIds);
        }

        /**
         * Retrieves a Schedule by its ID.
         *
         * @param scheduleId the ID of the Schedule to retrieve
         * @return a {@link CustomResponse} containing the Schedule details
         */
        @GetMapping("/{scheduleId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<ScheduleResponse> getScheduleById(@PathVariable @UUID final String scheduleId) {

                final Schedule schedule = scheduleReadService.getScheduleById(scheduleId);

                final ScheduleResponse scheduleResponse = scheduleToScheduleResponseMapper.map(schedule);

                return CustomResponse.successOf(scheduleResponse);

        }

        /**
         * Retrieves a paginated list of Schedules based on the paging request.
         *
         * @param schedulePagingRequest the request payload containing paging
         *                              information
         * @return a {@link CustomResponse} containing the paginated list of Schedules
         */
        @PostMapping
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<CustomPagingResponse<ScheduleResponse>> getSchedules(
                        @RequestBody @Valid final SchedulePagingRequest schedulePagingRequest) {

                final CustomPage<Schedule> schedulePage = scheduleReadService.getSchedules(schedulePagingRequest);

                final CustomPagingResponse<ScheduleResponse> schedulePagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(schedulePage);

                return CustomResponse.successOf(schedulePagingResponse);

        }

        /**
         * Retrieves a paginated list of Schedules based on the paging request.
         *
         * @param schedulePagingRequest the request payload containing paging
         *                              information
         * @return a {@link CustomResponse} containing the paginated list of Schedules
         */
        @PostMapping("/historiques")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public CustomResponse<CustomPagingResponse<ScheduleResponse>> getHistoriques(
                        @RequestBody @Valid final SchedulePagingHistoriqueRequest schedulePagingHistoriqueRequest) {

                final CustomPage<Schedule> schedulePage = scheduleHistoriqueService
                                .getHistoriques(schedulePagingHistoriqueRequest);

                final CustomPagingResponse<ScheduleResponse> schedulePagingResponse = customPageToCustomPagingResponseMapper
                                .toPagingResponse(schedulePage);

                return CustomResponse.successOf(schedulePagingResponse);

        }

        /**
         * Updates an existing Schedule by its ID.
         *
         * @param scheduleUpdateRequest the request payload containing updated Schedule
         *                              details
         * @param scheduleId            the ID of the Schedule to update
         * @return a {@link CustomResponse} containing the updated Schedule details
         */
        @PutMapping("/{scheduleId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<ScheduleResponse> updatedScheduleById(
                        @RequestBody @Valid final ScheduleUpdateRequest scheduleUpdateRequest,
                        @PathVariable @UUID final String scheduleId) {

                final Schedule updatedSchedule = scheduleUpdateService.updateScheduleById(scheduleId,
                                scheduleUpdateRequest);

                final ScheduleResponse scheduleResponse = scheduleToScheduleResponseMapper.map(updatedSchedule);

                return CustomResponse.successOf(scheduleResponse);
        }

        /**
         * Deletes a Schedule by its ID.
         *
         * @param scheduleId the ID of the Schedule to delete
         * @return a {@link CustomResponse} indicating successful deletion
         */
        @DeleteMapping("/{scheduleId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<Void> deleteScheduleById(@PathVariable @UUID final String scheduleId) {

                scheduleDeleteService.deleteScheduleById(scheduleId);
                return CustomResponse.SUCCESS;
        }
 
        @DeleteMapping("/all")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public CustomResponse<Void> deleteSchedulesByIds(@RequestBody List<@UUID String> scheduleIds) {
                scheduleDeleteService.deleteSchedulesByIds(scheduleIds);
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
                return scheduleRepository.existsByPortFromIdAndActive(id, "1")
                                || scheduleRepository.existsByPortToIdAndActive(id, "1");
        }

        @GetMapping("/companies/{id}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
        public boolean existsByCompanyId(@PathVariable String id) {
                return scheduleRepository.existsByCompanyIdAndActive(id, "1");
        }

        /**
         * Retrieves statistics for the current user, which are the number of schedules
         * for today, number of schedules for yesterday, and the score of the user
         * calculated by adding the number of schedules for today and the number of
         * schedules for yesterday. If the user is an admin, the statistics are
         * retrieved for all users.
         *
         * @return a {@link CustomResponse} containing the statistics
         */

        @GetMapping("/stats")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public DashboardResponse getScheduleStats() {
                return scheduleDashboardService.getScheduleStats();
        }

        @GetMapping("/graphics")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public List<WeeklyScheduleData> getWeeklyScheduleStats() {
                return scheduleDashboardService.getGraphicSchedules();
        }

        @GetMapping("/graphic-companies")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public List<WeeklyScheduleByCompaniesData> getWeeklyScheduleByCompaniesStats() {
                return scheduleDashboardService.getGraphicScheduleByCompanies();
        }

        
        @GetMapping("/graphic-users")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public List<DailyScheduleByUsersData> getDaillyScheduleByUsersStats() {
                return scheduleDashboardService.getDaillyScheduleByUsers();
        }
}
