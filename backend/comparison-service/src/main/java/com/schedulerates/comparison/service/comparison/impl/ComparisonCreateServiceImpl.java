package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.client.SettingServiceClient;
import com.schedulerates.comparison.model.auth.enums.TokenClaims;
import com.schedulerates.comparison.model.common.dto.client.CompanyDto;
import com.schedulerates.comparison.model.common.dto.client.ContainerDto;
import com.schedulerates.comparison.model.common.dto.client.GargoDto;
import com.schedulerates.comparison.model.common.dto.client.PortDto;
import com.schedulerates.comparison.model.common.dto.client.TransportationDto;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonCreateRequest;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import com.schedulerates.comparison.model.comparison.mapper.ComparisonEntityToComparisonMapper;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.service.comparison.ComparisonCreateService;

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
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation named {@link ScheduleCreateServiceImpl} for creating
 * Schedules.
 */
@Service
@RequiredArgsConstructor
public class ComparisonCreateServiceImpl implements ComparisonCreateService {

        private final ComparisonRepository comparisonRepository;
        private final SettingServiceClient settingServiceClient;
        private final ComparisonEntityToComparisonMapper comparisonEntityToComparisonMapper = ComparisonEntityToComparisonMapper
                        .initialize();
        private final HttpServletRequest httpServletRequest;

        /**
         * Creates a new Comparison based on the provided Comparison creation request.
         *
         * @param comparisonCreateRequest The request containing data to create the
         *                                comparison.
         * @return The created Comparison object.
         */
        @Override
        @Transactional
        public List<Comparison> createComparison(ComparisonCreateRequest comparisonCreateRequest) {
                List<Comparison> createdComparisons = new ArrayList<>();
                String authHeader = httpServletRequest.getHeader("Authorization");

                // Formatteur pour parser les dates en format "dd/MM/yyyy"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                .withLocale(Locale.ENGLISH);

                // Fetch reference data once since these are the same for all records
                PortDto portFrom = settingServiceClient
                                .getPortById(comparisonCreateRequest.getPortFromId(), authHeader)
                                .getResponse();
                PortDto portTo = settingServiceClient
                                .getPortById(comparisonCreateRequest.getPortToId(), authHeader)
                                .getResponse();
                CompanyDto company = settingServiceClient
                                .getCompanyById(comparisonCreateRequest.getCompanyId(), authHeader)
                                .getResponse();
                TransportationDto transportation = settingServiceClient
                                .gettransportationById(comparisonCreateRequest.getTransportationId(), authHeader)
                                .getResponse();
                GargoDto gargo = settingServiceClient
                                .getGargoById(comparisonCreateRequest.getGargoId(), authHeader)
                                .getResponse();

                // For each container and price combination
                for (int i = 0; i < comparisonCreateRequest.getContainerId().size(); i++) {
                        String containerId = comparisonCreateRequest.getContainerId().get(i);
                        Integer price = comparisonCreateRequest.getPrice().get(i);

                        // Get container details
                        ContainerDto container = settingServiceClient
                                        .getContainerById(containerId, authHeader)
                                        .getResponse();

                        // For each date combination
                        for (int j = 0; j < comparisonCreateRequest.getDateDepart().size(); j++) {
                                String dateDepartStr = comparisonCreateRequest.getDateDepart().get(j);
                                String dateArriveStr = comparisonCreateRequest.getDateArrive().get(j);

                                LocalDate departDate = LocalDate.parse(dateDepartStr, formatter);
                                LocalDate arriveDate = LocalDate.parse(dateArriveStr, formatter);

                                // Validate dates and ports
                                if ((departDate.isBefore(arriveDate) || departDate.isEqual(arriveDate))) {

                                        // Check if an active record already exists with these values
                                        boolean exists = comparisonRepository
                                                        .existsByPortFromIdAndPortToIdAndCompanyIdAndContainerIdAndDateDepartAndDateArriveAndActive(
                                                                        comparisonCreateRequest.getPortFromId(),
                                                                        comparisonCreateRequest.getPortToId(),
                                                                        comparisonCreateRequest.getCompanyId(),
                                                                        containerId,
                                                                        dateDepartStr,
                                                                        dateArriveStr,
                                                                        "1"); // active = 1

                                        if (!exists && !comparisonCreateRequest.getPortFromId().equals(comparisonCreateRequest.getPortToId())) {
                                                ComparisonEntity comparisonEntity = ComparisonEntity.builder()
                                                                .portFromId(comparisonCreateRequest.getPortFromId())
                                                                .portToId(comparisonCreateRequest.getPortToId())
                                                                .companyId(comparisonCreateRequest.getCompanyId())
                                                                .transportationId(comparisonCreateRequest
                                                                                .getTransportationId())
                                                                .gargoId(comparisonCreateRequest.getGargoId())
                                                                .containerId(containerId)
                                                                .dateDepart(dateDepartStr)
                                                                .dateArrive(dateArriveStr)
                                                                .price(price)
                                                                .portFromName(portFrom.getPortName())
                                                                .portToName(portTo.getPortName())
                                                                .companyName(company.getCompanyName())
                                                                .containerName(container.getContainerName())
                                                                .gargoName(gargo.getGargoName())
                                                                .transportationName(
                                                                                transportation.getTransportationName())
                                                                .createdBy(getCurrentUserEmail())
                                                                .createdAt(LocalDateTime.now())
                                                                .active("1")
                                                                .build();

                                                ComparisonEntity savedEntity = comparisonRepository
                                                                .save(comparisonEntity);
                                                createdComparisons.add(
                                                                comparisonEntityToComparisonMapper.map(savedEntity));
                                        }
                                }
                        }
                }

                return createdComparisons;
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