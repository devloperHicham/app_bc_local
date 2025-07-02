package com.schedulerates.comparison.model.comparison.mapper;

import com.schedulerates.comparison.model.common.mapper.BaseMapper;
import com.schedulerates.comparison.model.comparison.Comparison;
import com.schedulerates.comparison.model.comparison.dto.response.ComparisonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link TransportationToTransportationResponseMapper} for converting {@link Transportation} to {@link TransportationResponse}.
 */
@Mapper
public interface ComparisonToComparisonResponseMapper extends BaseMapper<Comparison, ComparisonResponse> {

    /**
     * Maps Comparison to ComparisonResponse.
     *
     * @param source The Comparison object to map.
     * @return ComparisonResponse object containing mapped data.
     */
    @Override
    ComparisonResponse map(Comparison source);

    /**
     * Initializes and returns an instance of ComparisonToComparisonResponseMapper.
     *
     * @return Initialized ComparisonToComparisonResponseMapper instance.
     */
    static ComparisonToComparisonResponseMapper initialize() {
        return Mappers.getMapper(ComparisonToComparisonResponseMapper.class);
    }

}
