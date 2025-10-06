package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.exception.NotFoundException;
import com.schedulerates.comparison.model.auth.enums.TokenClaims;
import com.schedulerates.comparison.model.common.CustomPage;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonPagingRequest;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonClientPagingRequest;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import com.schedulerates.comparison.model.comparison.mapper.ListComparisonEntityToListComparisonMapper;
import com.schedulerates.comparison.model.comparison.mapper.ComparisonEntityToComparisonMapper;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.service.comparison.ComparisonReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComparisonReadServiceImpl implements ComparisonReadService {

        private final ComparisonRepository comparisonRepository;

        private final ComparisonEntityToComparisonMapper comparisonEntityToComparisonMapper = ComparisonEntityToComparisonMapper
                        .initialize();

        private final ListComparisonEntityToListComparisonMapper listComparisonEntityToListComparisonMapper = ListComparisonEntityToListComparisonMapper
                        .initialize();

        @Override
        public Comparison getComparisonById(String comparisonId) {
                final ComparisonEntity comparisonEntityFromDB = comparisonRepository
                                .findById(comparisonId)
                                .orElseThrow(() -> new NotFoundException("With given comparisonId = " + comparisonId));

                return comparisonEntityToComparisonMapper.map(comparisonEntityFromDB);
        }

        @Override
        public CustomPage<Comparison> getComparisons(ComparisonPagingRequest comparisonPagingRequest) {
                Page<ComparisonEntity> scheduleEntityPage;
                String currentUserEmail = getCurrentUserEmail();
                boolean isAdmin = isAdmin();

                if (comparisonPagingRequest.getSearch() != null && !comparisonPagingRequest.getSearch().isEmpty()) {
                        scheduleEntityPage = isAdmin
                                        ? comparisonRepository.findByTextSearch(comparisonPagingRequest.getSearch(),
                                                        comparisonPagingRequest.toPageable())
                                        : comparisonRepository.findByTextSearchAndCreatedBy(
                                                        comparisonPagingRequest.getSearch(),
                                                        currentUserEmail, comparisonPagingRequest.toPageable());
                } else if (hasFilterValues(comparisonPagingRequest)) {
                        scheduleEntityPage = isAdmin
                                        ? comparisonRepository.findByFilters(
                                                        comparisonPagingRequest.getPortFromId(),
                                                        comparisonPagingRequest.getPortToId(),
                                                        comparisonPagingRequest.getDateDepart(),
                                                        comparisonPagingRequest.getDateArrive(),
                                                        comparisonPagingRequest.getCompanyId(),
                                                        comparisonPagingRequest.toPageable())
                                        : comparisonRepository.findByFiltersAndCreatedBy(
                                                        comparisonPagingRequest.getPortFromId(),
                                                        comparisonPagingRequest.getPortToId(),
                                                        comparisonPagingRequest.getDateDepart(),
                                                        comparisonPagingRequest.getDateArrive(),
                                                        comparisonPagingRequest.getCompanyId(),
                                                        currentUserEmail,
                                                        comparisonPagingRequest.toPageable());
                } else {
                        scheduleEntityPage = isAdmin
                                        ? comparisonRepository.findTodayActive("1",
                                                        comparisonPagingRequest.toPageable())
                                        : comparisonRepository.findByActiveAndCreatedByToday("1", currentUserEmail,
                                                        comparisonPagingRequest.toPageable());
                }

                final List<Comparison> comparisonDomainModels = listComparisonEntityToListComparisonMapper
                                .toComparisonList(scheduleEntityPage.getContent());

                return CustomPage.of(comparisonDomainModels, scheduleEntityPage);
        }

        private boolean hasFilterValues(ComparisonPagingRequest comparisonPagingRequest) {
                return (comparisonPagingRequest.getPortFromId() != null
                                && !comparisonPagingRequest.getPortFromId().isEmpty())
                                ||
                                (comparisonPagingRequest.getPortToId() != null
                                                && !comparisonPagingRequest.getPortToId().isEmpty())
                                ||
                                (comparisonPagingRequest.getDateDepart() != null
                                                && !comparisonPagingRequest.getDateDepart().isEmpty())
                                ||
                                (comparisonPagingRequest.getDateArrive() != null
                                                && !comparisonPagingRequest.getDateArrive().isEmpty())
                                ||
                                (comparisonPagingRequest.getCompanyId() != null
                                                && !comparisonPagingRequest.getCompanyId().isEmpty());
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

        private boolean isAdmin() {
                return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                                .map(Authentication::getPrincipal)
                                .filter(user -> !ANONYMOUS_USER.equals(user))
                                .map(Jwt.class::cast)
                                .map(jwt -> jwt.getClaim(TokenClaims.USER_TYPE.getValue()).toString())
                                .orElse(ANONYMOUS_USER)
                                .equals("ADMIN");
        }

        @Override
        public CustomPage<Comparison> getComparisonClients(ComparisonClientPagingRequest comparisonPagingRequest) {
                
                Page<ComparisonEntity> comparisonEntity = comparisonRepository.findByComparisonFilters(
                                comparisonPagingRequest.getSelectedPortFromComparison(),
                                comparisonPagingRequest.getSelectedPortToComparison(),
                                comparisonPagingRequest.getStartDateComparison(),
                                comparisonPagingRequest.getEndDateComparison(),
                                comparisonPagingRequest.getSelectedTransportation(),
                                comparisonPagingRequest.getSelectedContainer(), 
                                comparisonPagingRequest.getIsCheapest(),
                                comparisonPagingRequest.getIsFastest(),
                                comparisonPagingRequest.getIsDirect(),
                                comparisonPagingRequest.toPageable());

                final List<Comparison> comparisonDomainModels = listComparisonEntityToListComparisonMapper
                                .toComparisonList(comparisonEntity.getContent());

                return CustomPage.of(comparisonDomainModels, comparisonEntity);
        }
}
