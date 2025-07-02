package com.schedulerates.schedule.service.schedule.impl;

import com.schedulerates.schedule.client.SettingServiceClient;
import com.schedulerates.schedule.model.auth.enums.TokenClaims;
import com.schedulerates.schedule.model.common.dto.client.CompanyDto;
import com.schedulerates.schedule.model.common.dto.client.PortDto;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleCreateRequest;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import com.schedulerates.schedule.model.schedule.mapper.ScheduleEntityToScheduleMapper;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleCreateService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link ScheduleCreateServiceImpl} for creating
 * Schedules.
 */
@Service
@RequiredArgsConstructor
public class ScheduleCreateServiceImpl implements ScheduleCreateService {

        private final ScheduleRepository scheduleRepository;

        private final SettingServiceClient settingServiceClient;

        private final ScheduleEntityToScheduleMapper scheduleEntityToScheduleMapper = ScheduleEntityToScheduleMapper
                        .initialize();

        private final HttpServletRequest httpServletRequest; // Inject the current request

        /**
         * Creates a new Schedule based on the provided Schedule creation request.
         *
         * @param scheduleCreateRequest The request containing data to create the
         *                              Schedule.
         * @return The created Schedule object.
         * @throws ScheduleAlreadyExistException If a Schedule with the same name
         *                                       already exists.
         */
        @Override
        public List<Schedule> createSchedule(ScheduleCreateRequest scheduleCreateRequest) {
                List<Schedule> createdSchedules = new ArrayList<>();
                String authHeader = httpServletRequest.getHeader("Authorization");

                // Formatteur pour parser les dates en format "dd/MM/yyyy"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                .withLocale(Locale.ENGLISH);

                // Fetch reference data once since these are the same for all records
                PortDto portFrom = settingServiceClient
                                .getPortById(scheduleCreateRequest.getPortFromId(), authHeader)
                                .getResponse();
                PortDto portTo = settingServiceClient
                                .getPortById(scheduleCreateRequest.getPortToId(), authHeader)
                                .getResponse();
                CompanyDto company = settingServiceClient
                                .getCompanyById(scheduleCreateRequest.getCompanyId(), authHeader)
                                .getResponse();

                // For each container and price combination
                for (int i = 0; i < scheduleCreateRequest.getRefVoyage().size(); i++) {
                        Integer transit = scheduleCreateRequest.getTransit().get(i);
                        String vessel = scheduleCreateRequest.getVessel().get(i);
                        String refVoyage = scheduleCreateRequest.getRefVoyage().get(i);
                        String serviceName = scheduleCreateRequest.getServiceName().get(i);

                        // For each date combination
                        for (int j = 0; j < scheduleCreateRequest.getDateDepart().size(); j++) {
                                String dateDepartStr = scheduleCreateRequest.getDateDepart().get(j);
                                String dateArriveStr = scheduleCreateRequest.getDateArrive().get(j);

                                LocalDate departDate = LocalDate.parse(dateDepartStr, formatter);
                                LocalDate arriveDate = LocalDate.parse(dateArriveStr, formatter);
                                // Check if an active record already exists with these values
                                boolean exists = scheduleRepository
                                                .existsByPortFromIdAndPortToIdAndRefVoyageAndDateDepartAndDateArriveAndActive(
                                                                scheduleCreateRequest.getPortFromId(),
                                                                scheduleCreateRequest.getPortToId(),
                                                                scheduleCreateRequest.getRefVoyage().get(j),
                                                                dateDepartStr,
                                                                dateArriveStr,
                                                                "1"); // active = 1
                                if (!exists && (departDate.isBefore(arriveDate) || departDate.isEqual(arriveDate))
                                                && !scheduleCreateRequest.getPortFromId()
                                                                .equals(scheduleCreateRequest.getPortToId())) {
                                        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                                                        .portFromId(scheduleCreateRequest.getPortFromId())
                                                        .portToId(scheduleCreateRequest.getPortToId())
                                                        .companyId(scheduleCreateRequest.getCompanyId())
                                                        .dateDepart(dateDepartStr)
                                                        .dateArrive(dateArriveStr)
                                                        .transit(transit)
                                                        .vessel(vessel)
                                                        .refVoyage(refVoyage)
                                                        .serviceName(serviceName)
                                                        .portFromName(portFrom.getPortName())
                                                        .portToName(portTo.getPortName())
                                                        .companyName(company.getCompanyName())
                                                        .createdBy(getCurrentUserEmail())
                                                        .createdAt(LocalDateTime.now())
                                                        .build();

                                        ScheduleEntity savedEntity = scheduleRepository.save(scheduleEntity);
                                        createdSchedules.add(scheduleEntityToScheduleMapper.map(savedEntity));
                                }
                        }
                }

                return createdSchedules;
        }

        private static final String ANONYMOUS_USER = "anonymousUser";

        private String getCurrentUserEmail() {
                return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                                .map(Authentication::getPrincipal)
                                .filter(user -> !ANONYMOUS_USER.equals(user))
                                .map(Jwt.class::cast)
                                .map(jwt -> jwt.getClaim(TokenClaims.USER_EMAIL.getValue()).toString())
                                .orElse(ANONYMOUS_USER);
        }

}
