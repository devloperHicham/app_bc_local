package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.exception.NotFoundException;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.service.comparison.ComparisonDeleteService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link ScheduleDeleteServiceImpl} for deleting
 * Schedules.
 */
@Service
@RequiredArgsConstructor
public class ComparisonDeleteServiceImpl implements ComparisonDeleteService {

    private final ComparisonRepository comparisonRepository;

    /**
     * Deletes a Comparison identified by its unique ID.
     *
     * @param comparisonId The ID of the Comparison to delete.
     * @throws NotFoundException If no Comparison with the given ID exists.
     */
    @Override
    public void deleteComparisonById(String comparisonId) {

        final ComparisonEntity comparisonEntityToBeDelete = comparisonRepository
                .findById(comparisonId)
                .orElseThrow(() -> new NotFoundException("With given comparisonId = " + comparisonId));

        comparisonRepository.delete(comparisonEntityToBeDelete);

    }
    /**
     * Deletes Comparisons identified by multiple IDs.
     *
     * @param comparisonIds The list of IDs of the Comparisons to delete.
     * @throws NotFoundException If any of the Comparison IDs do not exist.
     */
    @Override
    public void deleteComparisonsByIds(List<String> comparisonIds) {
        List<ComparisonEntity> entities = comparisonRepository.findAllById(comparisonIds);

        if (entities.size() != comparisonIds.size()) {
            throw new NotFoundException("Some comparison IDs were not found.");
        }

        comparisonRepository.deleteAll(entities);
    }
}
