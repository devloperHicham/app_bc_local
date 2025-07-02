package com.schedulerates.schedule.service.schedule.impl;

import com.schedulerates.schedule.model.common.CustomPage;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.SchedulePagingHistoriqueRequest;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import com.schedulerates.schedule.model.schedule.mapper.ListScheduleEntityToListScheduleMapper;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleHistoriqueService;
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
public class ScheduleHistoriqueServiceImpl implements ScheduleHistoriqueService {

        private final ScheduleRepository scheduleRepository;

        private final ListScheduleEntityToListScheduleMapper listScheduleEntityToListScheduleMapper = ListScheduleEntityToListScheduleMapper
                        .initialize();

        @Override
        public CustomPage<Schedule> getHistoriques(SchedulePagingHistoriqueRequest schedulePagingHistoriqueRequest) {
                Page<ScheduleEntity> scheduleEntityPage;

                if (schedulePagingHistoriqueRequest.getSearch() != null
                                && !schedulePagingHistoriqueRequest.getSearch().isEmpty()) {
                        scheduleEntityPage = scheduleRepository.findByTextSearch(
                                        schedulePagingHistoriqueRequest.getSearch(),
                                        schedulePagingHistoriqueRequest.toPageable());

                } else if (hasFilterValues(schedulePagingHistoriqueRequest)) {
                        // Formatteur pour parser les dates en format "dd/MM/yyyy"
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                        .withLocale(Locale.ENGLISH);

                        LocalDateTime dateDebut = null;
                        LocalDateTime dateFin = null;

                        if (schedulePagingHistoriqueRequest.getDateDebut() != null
                                        && !schedulePagingHistoriqueRequest.getDateDebut().isEmpty()) {
                                dateDebut = LocalDate.parse(
                                                schedulePagingHistoriqueRequest.getDateDebut(),
                                                formatter)
                                                .atStartOfDay(); // Start of day (00:00:00)
                        }

                        if (schedulePagingHistoriqueRequest.getDateFin() != null
                                        && !schedulePagingHistoriqueRequest.getDateFin().isEmpty()) {
                                dateFin = LocalDate.parse(
                                                schedulePagingHistoriqueRequest.getDateFin(),
                                                formatter)
                                                .atTime(23, 59, 59); // End of day (23:59:59)
                        }

                        // Call repository with LocalDate parameters
                        scheduleEntityPage = scheduleRepository.findByFilterHistoriques(
                                        schedulePagingHistoriqueRequest.getPortFromId(),
                                        schedulePagingHistoriqueRequest.getPortToId(),
                                        dateDebut, // Now LocalDate
                                        dateFin, // Now LocalDate
                                        schedulePagingHistoriqueRequest.getUserId(),
                                        schedulePagingHistoriqueRequest.toPageable());
                } else {
                        LocalDate yesterday = LocalDate.now().minusDays(1);
                        LocalDateTime startOfDay = yesterday.atStartOfDay(); // 00:00:00
                        LocalDateTime startOfNextDay = yesterday.plusDays(1).atStartOfDay(); // Next day 00:00:00
                        scheduleEntityPage = scheduleRepository.findYesterdayActive("1", startOfDay, startOfNextDay,
                                        schedulePagingHistoriqueRequest.toPageable());
                }

                final List<Schedule> scheduleDomainModels = listScheduleEntityToListScheduleMapper
                                .toScheduleList(scheduleEntityPage.getContent());

                return CustomPage.of(scheduleDomainModels, scheduleEntityPage);
        }

        private boolean hasFilterValues(SchedulePagingHistoriqueRequest schedulePagingHistoriqueRequest) {
                return (schedulePagingHistoriqueRequest.getPortFromId() != null
                                && !schedulePagingHistoriqueRequest.getPortFromId().isEmpty()) ||
                                (schedulePagingHistoriqueRequest.getPortToId() != null
                                                && !schedulePagingHistoriqueRequest.getPortToId().isEmpty())
                                ||
                                (schedulePagingHistoriqueRequest.getDateDebut() != null
                                                && !schedulePagingHistoriqueRequest.getDateDebut().isEmpty())
                                ||
                                (schedulePagingHistoriqueRequest.getDateFin() != null
                                                && !schedulePagingHistoriqueRequest.getDateFin().isEmpty())
                                ||
                                (schedulePagingHistoriqueRequest.getUserId() != null
                                                && !schedulePagingHistoriqueRequest.getUserId().isEmpty());
        }

}
