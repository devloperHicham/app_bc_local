package com.schedulerates.schedule.service.schedule.impl;

import com.schedulerates.schedule.exception.NotFoundException;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import com.schedulerates.schedule.repository.ScheduleRepository;
import com.schedulerates.schedule.service.schedule.ScheduleDeleteService;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
/**
     * Deletes Schedules identified by multiple IDs.
     *
     * @param scheduleIds The list of IDs of the Schedules to delete.
     * @throws NotFoundException If any of the Schedule IDs do not exist.
     */
    @Override
    public void deleteSchedulesByIds(List<String> scheduleIds) {
        List<ScheduleEntity> entities = scheduleRepository.findAllById(scheduleIds);

        if (entities.size() != scheduleIds.size()) {
            throw new NotFoundException("Some schedule IDs were not found.");
        }

        scheduleRepository.deleteAll(entities);
    }
}
