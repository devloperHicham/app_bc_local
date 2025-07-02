package com.schedulerates.comparison.service.comparison.impl;

import com.schedulerates.comparison.exception.NotFoundException;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonUpdateRequest;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import com.schedulerates.comparison.model.comparison.mapper.ComparisonEntityToComparisonMapper;
import com.schedulerates.comparison.model.comparison.mapper.ComparisonUpdateRequestToComparisonEntityMapper;
import com.schedulerates.comparison.repository.ComparisonRepository;
import com.schedulerates.comparison.service.comparison.ComparisonUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link TransportationUpdateServiceImpl} for updating Transportations.
 */
@Service
@RequiredArgsConstructor
public class ComparisonUpdateServiceImpl implements ComparisonUpdateService {

    private final ComparisonRepository comparisonRepository;

    private final ComparisonUpdateRequestToComparisonEntityMapper comparisonUpdateRequestToComparisonEntityMapper =
            ComparisonUpdateRequestToComparisonEntityMapper.initialize();

    private final ComparisonEntityToComparisonMapper comparisonEntityToComparisonMapper =
            ComparisonEntityToComparisonMapper.initialize();

    /**
     * Updates a Comparison identified by its unique ID using the provided update request.
     *
     * @param comparisonId           The ID of the Comparison to update.
     * @param comparisonUpdateRequest The request containing updated data for the comparison.
     * @return The updated Comparison object.
     * @throws ComparisonNotFoundException If no Comparison with the given ID exists.
     * @throws ComparisonAlreadyExistException If another Comparison with the updated name already exists.
     */
    @Override
    public Comparison updateComparisonById(String comparisonId, ComparisonUpdateRequest comparisonUpdateRequest) {

        final ComparisonEntity comparisonEntityToBeUpdate = comparisonRepository
                .findById(comparisonId)
                .orElseThrow(() -> new NotFoundException("With given comparisonID = " + comparisonId));

        comparisonUpdateRequestToComparisonEntityMapper.mapForUpdating(comparisonEntityToBeUpdate, comparisonUpdateRequest);

        ComparisonEntity updatedComparisonEntity = comparisonRepository.save(comparisonEntityToBeUpdate);

        return comparisonEntityToComparisonMapper.map(updatedComparisonEntity);

    }
}
