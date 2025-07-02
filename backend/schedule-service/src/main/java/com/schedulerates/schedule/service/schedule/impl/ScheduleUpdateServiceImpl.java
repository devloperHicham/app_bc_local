package com.schedulerates.schedule.service.schedule.impl;

import com.schedulerates.schedule.exception.NotFoundException;
import com.schedulerates.schedule.model.schedule.Schedule;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleUpdateRequest;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import com.schedulerates.schedule.model.schedule.mapper.ScheduleEntityToScheduleMapper;
import com.schedulerates.schedule.model.schedule.mapper.ScheduleUpdateRequestToScheduleEntityMapper;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link TransportationUpdateServiceImpl} for updating Transportations.
 */
@Service
@RequiredArgsConstructor
public class ScheduleUpdateServiceImpl implements ScheduleUpdateService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleUpdateRequestToScheduleEntityMapper scheduleUpdateRequestToScheduleEntityMapper =
            ScheduleUpdateRequestToScheduleEntityMapper.initialize();

    private final ScheduleEntityToScheduleMapper scheduleEntityToScheduleMapper =
            ScheduleEntityToScheduleMapper.initialize();

    /**
     * Updates a Schedule identified by its unique ID using the provided update request.
     *
     * @param scheduleId           The ID of the Schedule to update.
     * @param scheduleUpdateRequest The request containing updated data for the Schedule.
     * @return The updated Schedule object.
     * @throws ScheduleNotFoundException If no Schedule with the given ID exists.
     * @throws ScheduleAlreadyExistException If another Schedule with the updated name already exists.
     */
    @Override
    public Schedule updateScheduleById(String scheduleId, ScheduleUpdateRequest scheduleUpdateRequest) {

        final ScheduleEntity scheduleEntityToBeUpdate = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("With given scheduleID = " + scheduleId));

        scheduleUpdateRequestToScheduleEntityMapper.mapForUpdating(scheduleEntityToBeUpdate, scheduleUpdateRequest);

        ScheduleEntity updatedScheduleEntity = scheduleRepository.save(scheduleEntityToBeUpdate);

        return scheduleEntityToScheduleMapper.map(updatedScheduleEntity);

    }
}
