package com.schedulerates.comparison.model.comparison.mapper;

import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List; 
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListTransportationEntityToListTransportationMapper} for converting {@link List<TransportationEntity>} to {@link List<Transportation>}.
 */
@Mapper
public interface ListComparisonEntityToListComparisonMapper {

    ComparisonEntityToComparisonMapper comparisonEntityToComparisonMapper = Mappers.getMapper(ComparisonEntityToComparisonMapper.class);

    /**
     * Converts a list of ComparisonEntity objects to a list of Comparison objects.
     *
     * @param comparisonEntities The list of ComparisonEntity objects to convert.
     * @return List of Comparison objects containing mapped data.
     */
    default List<Comparison> toComparisonList(List<ComparisonEntity> comparisonEntities) {

        if (comparisonEntities == null) {
            return null;
        }

        return comparisonEntities.stream()
                .map(comparisonEntityToComparisonMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListComparisonEntityToListComparisonMapper.
     *
     * @return Initialized ListComparisonEntityToListComparisonMapper instance.
     */
    static ListComparisonEntityToListComparisonMapper initialize() {
        return Mappers.getMapper(ListComparisonEntityToListComparisonMapper.class);
    }

}
