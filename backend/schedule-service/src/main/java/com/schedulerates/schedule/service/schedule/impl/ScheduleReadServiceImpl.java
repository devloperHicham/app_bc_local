package com.schedulerates.schedule.service.schedule.impl;

import com.schedulerates.schedule.exception.NotFoundException;
import com.schedulerates.schedule.model.auth.enums.TokenClaims;
import com.schedulerates.schedule.model.common.CustomPage;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.SchedulePagingRequest;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import com.schedulerates.schedule.model.schedule.mapper.ListScheduleEntityToListScheduleMapper;
import com.schedulerates.schedule.model.schedule.mapper.ScheduleEntityToScheduleMapper;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleReadServiceImpl implements ScheduleReadService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleEntityToScheduleMapper scheduleEntityToScheduleMapper = ScheduleEntityToScheduleMapper
            .initialize();

    private final ListScheduleEntityToListScheduleMapper listScheduleEntityToListScheduleMapper = ListScheduleEntityToListScheduleMapper
            .initialize();

    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public Schedule getScheduleById(String scheduleId) {
        final ScheduleEntity scheduleEntityFromDB = scheduleRepository
                .findByIdAndActive(scheduleId, "1")
                .orElseThrow(() -> new NotFoundException("With given scheduleId = " + scheduleId));
        return scheduleEntityToScheduleMapper.map(scheduleEntityFromDB);
    }

    @Override
    public CustomPage<Schedule> getSchedules(SchedulePagingRequest schedulePagingRequest) {
        Page<ScheduleEntity> scheduleEntityPage;
        String currentUserEmail = getCurrentUserEmail();
        boolean isAdmin = isAdmin();

        if (schedulePagingRequest.getSearch() != null && !schedulePagingRequest.getSearch().isEmpty()) {
            scheduleEntityPage = isAdmin
                    ? scheduleRepository.findByTextSearch(schedulePagingRequest.getSearch(),
                            schedulePagingRequest.toPageable())
                    : scheduleRepository.findByTextSearchAndCreatedBy(schedulePagingRequest.getSearch(),
                            currentUserEmail, schedulePagingRequest.toPageable());

        } else if (hasFilterValues(schedulePagingRequest)) {
            scheduleEntityPage = isAdmin
                    ? scheduleRepository.findByFilters(
                            schedulePagingRequest.getPortFromId(),
                            schedulePagingRequest.getPortToId(),
                            schedulePagingRequest.getDateDepart(),
                            schedulePagingRequest.getDateArrive(),
                            schedulePagingRequest.getCompanyId(),
                            schedulePagingRequest.toPageable())
                    : scheduleRepository.findByFiltersAndCreatedBy(
                            schedulePagingRequest.getPortFromId(),
                            schedulePagingRequest.getPortToId(),
                            schedulePagingRequest.getDateDepart(),
                            schedulePagingRequest.getDateArrive(),
                            schedulePagingRequest.getCompanyId(),
                            currentUserEmail,
                            schedulePagingRequest.toPageable());
        } else {
            scheduleEntityPage = isAdmin
                    ? scheduleRepository.findTodayActive("1", schedulePagingRequest.toPageable())
                    : scheduleRepository.findByActiveAndCreatedByToday("1", currentUserEmail,
                            schedulePagingRequest.toPageable());
        }

        final List<Schedule> scheduleDomainModels = listScheduleEntityToListScheduleMapper
                .toScheduleList(scheduleEntityPage.getContent());

        return CustomPage.of(scheduleDomainModels, scheduleEntityPage);
    }

    private boolean hasFilterValues(SchedulePagingRequest schedulePagingRequest) {
        return (schedulePagingRequest.getPortFromId() != null && !schedulePagingRequest.getPortFromId().isEmpty()) ||
                (schedulePagingRequest.getPortToId() != null && !schedulePagingRequest.getPortToId().isEmpty()) ||
                (schedulePagingRequest.getDateDepart() != null && !schedulePagingRequest.getDateDepart().isEmpty()) ||
                (schedulePagingRequest.getDateArrive() != null && !schedulePagingRequest.getDateArrive().isEmpty()) ||
                (schedulePagingRequest.getCompanyId() != null && !schedulePagingRequest.getCompanyId().isEmpty());
    }

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
                .equalsIgnoreCase("ADMIN");
    }
}
