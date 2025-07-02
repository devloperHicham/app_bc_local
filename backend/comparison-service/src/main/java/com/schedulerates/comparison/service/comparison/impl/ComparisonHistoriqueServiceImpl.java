package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.model.common.CustomPage;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonPagingHistoriqueRequest;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import com.schedulerates.comparison.model.comparison.mapper.ListComparisonEntityToListComparisonMapper;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.service.comparison.ComparisonHistoriqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ComparisonHistoriqueServiceImpl implements ComparisonHistoriqueService {

        private final ComparisonRepository comparisonRepository;

        private final ListComparisonEntityToListComparisonMapper listComparisonEntityToListComparisonMapper = ListComparisonEntityToListComparisonMapper
                        .initialize();

        @Override
        public CustomPage<Comparison> getHistoriques(ComparisonPagingHistoriqueRequest comparisonPagingHistoriqueRequest) {
                Page<ComparisonEntity> comparisonEntityPage;

                if (comparisonPagingHistoriqueRequest.getSearch() != null
                                && !comparisonPagingHistoriqueRequest.getSearch().isEmpty()) {
                        comparisonEntityPage = comparisonRepository.findByTextSearch(
                                        comparisonPagingHistoriqueRequest.getSearch(),
                                        comparisonPagingHistoriqueRequest.toPageable());

                } else if (hasFilterValues(comparisonPagingHistoriqueRequest)) {
                        // Formatteur pour parser les dates en format "dd/MM/yyyy"
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                        .withLocale(Locale.ENGLISH);

                        LocalDateTime dateDebut = null;
                        LocalDateTime dateFin = null;

                        if (comparisonPagingHistoriqueRequest.getDateDebut() != null
                                        && !comparisonPagingHistoriqueRequest.getDateDebut().isEmpty()) {
                                dateDebut = LocalDate.parse(
                                                comparisonPagingHistoriqueRequest.getDateDebut(),
                                                formatter)
                                                .atStartOfDay(); // Start of day (00:00:00)
                        }

                        if (comparisonPagingHistoriqueRequest.getDateFin() != null
                                        && !comparisonPagingHistoriqueRequest.getDateFin().isEmpty()) {
                                dateFin = LocalDate.parse(
                                                comparisonPagingHistoriqueRequest.getDateFin(),
                                                formatter)
                                                .atTime(23, 59, 59); // End of day (23:59:59)
                        }

                        // Call repository with LocalDate parameters
                        comparisonEntityPage = comparisonRepository.findByFilterHistoriques(
                                        comparisonPagingHistoriqueRequest.getPortFromId(),
                                        comparisonPagingHistoriqueRequest.getPortToId(),
                                        dateDebut, // Now LocalDate
                                        dateFin, // Now LocalDate
                                        comparisonPagingHistoriqueRequest.getUserId(),
                                        comparisonPagingHistoriqueRequest.toPageable());
                } else {
                        LocalDate yesterday = LocalDate.now().minusDays(1);
                        LocalDateTime startOfDay = yesterday.atStartOfDay(); // 00:00:00
                        LocalDateTime startOfNextDay = yesterday.plusDays(1).atStartOfDay(); // Next day 00:00:00
                        comparisonEntityPage = comparisonRepository.findYesterdayActive("1", startOfDay, startOfNextDay,
                                        comparisonPagingHistoriqueRequest.toPageable());
                }

                final List<Comparison> comparisonDomainModels = listComparisonEntityToListComparisonMapper
                                .toComparisonList(comparisonEntityPage.getContent());

                return CustomPage.of(comparisonDomainModels, comparisonEntityPage);
        }

        private boolean hasFilterValues(ComparisonPagingHistoriqueRequest comparisonPagingHistoriqueRequest) {
                return (comparisonPagingHistoriqueRequest.getPortFromId() != null
                                && !comparisonPagingHistoriqueRequest.getPortFromId().isEmpty()) ||
                                (comparisonPagingHistoriqueRequest.getPortToId() != null
                                                && !comparisonPagingHistoriqueRequest.getPortToId().isEmpty())
                                ||
                                (comparisonPagingHistoriqueRequest.getDateDebut() != null
                                                && !comparisonPagingHistoriqueRequest.getDateDebut().isEmpty())
                                ||
                                (comparisonPagingHistoriqueRequest.getDateFin() != null
                                                && !comparisonPagingHistoriqueRequest.getDateFin().isEmpty())
                                ||
                                (comparisonPagingHistoriqueRequest.getUserId() != null
                                                && !comparisonPagingHistoriqueRequest.getUserId().isEmpty());
        }

}
