package com.schedulerates.comparison.model.comparison.mapper;

import com.schedulerates.comparison.model.common.mapper.BaseMapper;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonUpdateRequest;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link scheduleUpdateRequestToscheduleEntityMapper}
 * for updating {@link scheduleEntity} using
 * {@link ScheduleUpdateRequest}.
 */
@Mapper
public interface ComparisonUpdateRequestToComparisonEntityMapper extends BaseMapper<ComparisonUpdateRequest, ComparisonEntity> {

    /**
     * Maps fields from ComparisonUpdateRequest to update ComparisonEntity.
     *
     * @param comparisonEntityToBeUpdate The ComparisonEntity object to be updated.
     * @param comparisonUpdateRequest    The ComparisonUpdateRequest object containing
     *                                   updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(ComparisonEntity comparisonEntityToBeUpdate, ComparisonUpdateRequest comparisonUpdateRequest) {
        comparisonEntityToBeUpdate.setPortFromId(String.valueOf(comparisonUpdateRequest.getPortFromId()));
        comparisonEntityToBeUpdate.setPortToId(String.valueOf(comparisonUpdateRequest.getPortToId()));
        comparisonEntityToBeUpdate.setCompanyId(String.valueOf(comparisonUpdateRequest.getCompanyId()));
        comparisonEntityToBeUpdate.setContainerId(String.valueOf(comparisonUpdateRequest.getContainerId()));
        comparisonEntityToBeUpdate.setTransportationId(String.valueOf(comparisonUpdateRequest.getTransportationId()));
        comparisonEntityToBeUpdate.setGargoId(String.valueOf(comparisonUpdateRequest.getGargoId()));
        comparisonEntityToBeUpdate.setDateDepart(comparisonUpdateRequest.getDateDepart());
        comparisonEntityToBeUpdate.setDateArrive(comparisonUpdateRequest.getDateArrive());
        comparisonEntityToBeUpdate.setPrice(comparisonUpdateRequest.getPrice());

    }

    /**
     * Initializes and returns an instance of
     * ComparisonUpdateRequestToComparisonEntityMapper.
     *
     * @return Initialized ComparisonUpdateRequestToComparisonEntityMapper instance.
     */
    static ComparisonUpdateRequestToComparisonEntityMapper initialize() {
        return Mappers.getMapper(ComparisonUpdateRequestToComparisonEntityMapper.class);
    }

}
