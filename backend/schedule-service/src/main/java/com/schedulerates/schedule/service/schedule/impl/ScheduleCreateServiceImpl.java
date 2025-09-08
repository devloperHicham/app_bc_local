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
import jakarta.transaction.Transactional;
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
        private final HttpServletRequest httpServletRequest;

        @Override
        @Transactional
        public List<Schedule> createSchedule(ScheduleCreateRequest scheduleCreateRequest) {
                List<Schedule> createdSchedules = new ArrayList<>();
                String authHeader = httpServletRequest.getHeader("Authorization");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                .withLocale(Locale.ENGLISH);

                // Validate all lists have the same size
                if (scheduleCreateRequest.getRefVoyage().size() != scheduleCreateRequest.getTransit().size() ||
                                scheduleCreateRequest.getRefVoyage().size() != scheduleCreateRequest.getVessel().size()
                                ||
                                scheduleCreateRequest
                                                .getRefVoyage().size() != scheduleCreateRequest.getServiceName().size()
                                ||
                                scheduleCreateRequest.getRefVoyage().size() != scheduleCreateRequest.getDateDepart()
                                                .size()
                                ||
                                scheduleCreateRequest.getRefVoyage().size() != scheduleCreateRequest.getDateArrive()
                                                .size()) {
                        throw new IllegalArgumentException("All input lists must have the same size");
                }

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

                // Single loop since dates are paired with voyages 1:1
                for (int i = 0; i < scheduleCreateRequest.getRefVoyage().size(); i++) {
                        Integer transit = scheduleCreateRequest.getTransit().get(i);
                        String vessel = scheduleCreateRequest.getVessel().get(i);
                        String refVoyage = scheduleCreateRequest.getRefVoyage().get(i);
                        String serviceName = scheduleCreateRequest.getServiceName().get(i);
                        String dateDepartStr = scheduleCreateRequest.getDateDepart().get(i);
                        String dateArriveStr = scheduleCreateRequest.getDateArrive().get(i);

                        LocalDate departDate = LocalDate.parse(dateDepartStr, formatter);
                        LocalDate arriveDate = LocalDate.parse(dateArriveStr, formatter);

                        // Check if an active record already exists with these values
                        boolean exists = scheduleRepository
                                        .existsByPortFromIdAndPortToIdAndRefVoyageAndDateDepartAndDateArriveAndActive(
                                                        scheduleCreateRequest.getPortFromId(),
                                                        scheduleCreateRequest.getPortToId(),
                                                        refVoyage,
                                                        dateDepartStr,
                                                        dateArriveStr,
                                                        "1"); // active = 1

                        if (!exists && (departDate.isBefore(arriveDate) || departDate.isEqual(arriveDate))
                                        && (!scheduleCreateRequest.getPortFromId()
                                                        .equals(scheduleCreateRequest.getPortToId()))) {
                                ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                                                .portFromId(scheduleCreateRequest.getPortFromId())
                                                .portFromName(portFrom.getPortName())
                                                .countryFromName(portFrom.getCountryName())
                                                .countryFromNameAbbreviation(portFrom.getCountryNameAbbreviation())
                                                .portFromCode(portFrom.getPortCode())
                                                .portFromLatitude(portFrom.getPortLatitude())
                                                .portFromLongitude(portFrom.getPortLongitude())
                                                .portFromLogo(portFrom.getPortLogo())

                                                .portToId(scheduleCreateRequest.getPortToId())
                                                .portToName(portTo.getPortName())
                                                .countryToName(portTo.getCountryName())
                                                .countryToNameAbbreviation(portTo.getCountryNameAbbreviation())
                                                .portToCode(portTo.getPortCode())
                                                .portToLatitude(portTo.getPortLatitude())
                                                .portToLongitude(portTo.getPortLongitude())
                                                .portToLogo(portTo.getPortLogo())

                                                .companyId(scheduleCreateRequest.getCompanyId())
                                                .companyName(company.getCompanyName())
                                                .companyLogo(company.getCompanyLogo())
                                                .dateDepart(dateDepartStr)
                                                .dateArrive(dateArriveStr)
                                                .transit(transit)
                                                .vessel(vessel)
                                                .refVoyage(refVoyage)
                                                .serviceName(serviceName)
                                                .createdBy(getCurrentUserEmail())
                                                .createdAt(LocalDateTime.now())
                                                .active("1")
                                                .build();

                                ScheduleEntity savedEntity = scheduleRepository.save(scheduleEntity);
                                createdSchedules.add(scheduleEntityToScheduleMapper.map(savedEntity));
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