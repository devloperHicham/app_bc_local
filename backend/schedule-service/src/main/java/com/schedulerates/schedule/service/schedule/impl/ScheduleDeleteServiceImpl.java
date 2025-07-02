package com.schedulerates.schedule.service.schedule.impl;

import com.schedulerates.schedule.exception.NotFoundException;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link ScheduleDeleteServiceImpl} for deleting Schedules.
 */
@Service
@RequiredArgsConstructor
public class ScheduleDeleteServiceImpl implements ScheduleDeleteService {

    private final ScheduleRepository scheduleRepository;

    /**
     * Deletes a Schedule identified by its unique ID.
     *
     * @param scheduleId The ID of the Schedule to delete.
     * @throws ScheduleNotFoundException If no Schedule with the given ID exists.
     */
    @Override
    public void deleteScheduleById(String scheduleId) {

        final ScheduleEntity scheduleEntityToBeDelete = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("With given scheduleId = " + scheduleId));

        scheduleRepository.delete(scheduleEntityToBeDelete);

    }

}
