package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.model.auth.enums.TokenClaims;
import com.schedulerates.comparison.model.comparison.ClientComparison;
import com.schedulerates.comparison.model.comparison.dto.request.ClientComparisonCreateRequest;
import com.schedulerates.comparison.model.comparison.entity.ClientComparisonEntity;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import com.schedulerates.comparison.repository.ClientComparisonRepository;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.service.comparison.ClientComparisonCreateService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for creating Client Comparisons.
 */
@Service
@RequiredArgsConstructor
public class ClientComparisonCreateServiceImpl implements ClientComparisonCreateService {

    private final ClientComparisonRepository clientComparisonRepository;
    private final ComparisonRepository comparisonRepository;

    @Override
    @Transactional
    public List<ClientComparison> createComparison(ClientComparisonCreateRequest request) {
        List<ClientComparison> createdComparisons = new ArrayList<>();

        // 1️⃣ Fetch base comparison created by admin
        ComparisonEntity comparisonEntity = comparisonRepository.findById(request.getComparisonId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Comparison not found with ID: " + request.getComparisonId()));

        // 2️⃣ Build the client comparison entity
        ClientComparisonEntity entity = ClientComparisonEntity.builder()
                .portFromId(comparisonEntity.getPortFromId())
                .portFromName(comparisonEntity.getPortFromName())
                .countryFromName(comparisonEntity.getCountryFromName())
                .countryFromNameAbbreviation(comparisonEntity.getCountryFromNameAbbreviation())
                .portFromCode(comparisonEntity.getPortFromCode())
                .portFromLongitude(comparisonEntity.getPortFromLongitude())
                .portFromLatitude(comparisonEntity.getPortFromLatitude())
                .portFromLogo(comparisonEntity.getPortFromLogo())

                .portToId(comparisonEntity.getPortToId())
                .portToName(comparisonEntity.getPortToName())
                .countryToName(comparisonEntity.getCountryToName())
                .countryToNameAbbreviation(comparisonEntity.getCountryToNameAbbreviation())
                .portToCode(comparisonEntity.getPortToCode())
                .portToLongitude(comparisonEntity.getPortToLongitude())
                .portToLatitude(comparisonEntity.getPortToLatitude())
                .portToLogo(comparisonEntity.getPortToLogo())

                .companyId(comparisonEntity.getCompanyId())
                .companyName(comparisonEntity.getCompanyName())
                .companyLogo(comparisonEntity.getCompanyLogo())

                .transportationId(comparisonEntity.getTransportationId())
                .transportationName(comparisonEntity.getTransportationName())

                .gargoId(comparisonEntity.getGargoId())
                .gargoName(comparisonEntity.getGargoName())

                .containerId(comparisonEntity.getContainerId())
                .containerName(comparisonEntity.getContainerName())
                .containerWeight(comparisonEntity.getContainerWeight())

                .dateDepart(comparisonEntity.getDateDepart())
                .dateArrive(comparisonEntity.getDateArrive())
                .price(comparisonEntity.getPrice())
                .codeTransation(request.getCodeTransation())

                // Client-provided fields
                .commodityCode(request.getCommodityCode())
                .weight(request.getWeight())
                .weightType(request.getWeightType())
                .infoDetail(request.getInfoDetail())
                .insurance(request.getInsurance())
                .customsClearance(request.getCustomsClearance())
                .certification(request.getCertification())
                .inspectionService(request.getInspectionService())

                // Metadata
                .createdBy(getCurrentUserEmail())
                .createdAt(LocalDateTime.now())
                .active("1")
                .build();

        // 3️⃣ Save entity
        ClientComparisonEntity saved = clientComparisonRepository.save(entity);

        // 4️⃣ Map manually to your domain model (since no constructor exists)
        ClientComparison model = ClientComparison.builder()
                .id(saved.getId())
                .companyName(saved.getCompanyName())
                .commodityCode(saved.getCommodityCode())
                .weight(saved.getWeight())
                .price(saved.getPrice())
                .dateDepart(saved.getDateDepart())
                .dateArrive(saved.getDateArrive())
                .build();

        createdComparisons.add(model);

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
