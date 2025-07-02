package com.schedulerates.comparison.model.comparison.mapper;

import com.schedulerates.comparison.model.common.mapper.BaseMapper;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ScheduleEntityToScheduleMapper} for converting {@link ScheduleEntity} to {@link Schedule}.
 */
@Mapper
public interface ComparisonEntityToComparisonMapper extends BaseMapper<ComparisonEntity, Comparison> {

    /**
     * Maps ComparisonEntity to Comparison.
     *
     * @param source The ComparisonEntity object to map.
     * @return Comparison object containing mapped data.
     */
    @Override
    Comparison map(ComparisonEntity source);

    /**
     * Initializes and returns an instance of ComparisonEntityToComparisonMapper.
     *
     * @return Initialized ComparisonEntityToComparisonMapper instance.
     */
    static ComparisonEntityToComparisonMapper initialize() {
        return Mappers.getMapper(ComparisonEntityToComparisonMapper.class);
    }

}
