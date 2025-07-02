package com.schedulerates.comparison.model.comparison.mapper;

import com.schedulerates.comparison.model.common.mapper.BaseMapper;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonCreateRequest;
import com.schedulerates.comparison.model.comparison.dto.request.ComparisonUpdateRequest;
import com.schedulerates.comparison.model.comparison.entity.ComparisonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ScheduleCreateRequestToScheduleEntityMapper}
 * for converting {@link ScheduleCreateRequest} to {@link ScheduleEntity}.
 */
@Mapper
public interface ComparisonCreateRequestToComparisonEntityMapper extends BaseMapper<ComparisonUpdateRequest, ComparisonEntity> {

    /**
     * Maps ComparisonCreateRequest to ComparisonEntity for saving purposes.
     *
     * @param comparisonCreateRequest The ComparisonCreateRequest object to map.
     * @return ComparisonEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default ComparisonEntity mapForSaving(ComparisonCreateRequest comparisonCreateRequest) {
        return ComparisonEntity.builder()
                /* .portFromId(String.valueOf(comparisonCreateRequest.getPortFromId()))
                .portToId(String.valueOf(comparisonCreateRequest.getPortToId()))
                .companyId(String.valueOf(comparisonCreateRequest.getCompanyId()))
                .transportationId(String.valueOf(comparisonCreateRequest.getTransportationId()))
                .gargoId(String.valueOf(comparisonCreateRequest.getGargoId()))
                //.containerId(String.valueOf(comparisonCreateRequest.getContainerId()))
                //.dateDepart(String.valueOf(comparisonCreateRequest.getDateDepart()))
                //.dateArrive(String.valueOf(comparisonCreateRequest.getDateArrive()))
                //.price(comparisonCreateRequest.getPrice())
                // Initialize name fields with empty strings
                .portFromName("")
                .portToName("")
                .companyName("")
                .gargoName("")
                .transportationName("")
                .containerName("")*/
                .build();
    }

    /**
     * Initializes and returns an instance of
     * ComparisonCreateRequestToComparisonEntityMapper.
     *
     * @return Initialized ComparisonCreateRequestToComparisonEntityMapper instance.
     */
    static ComparisonCreateRequestToComparisonEntityMapper initialize() {
        return Mappers.getMapper(ComparisonCreateRequestToComparisonEntityMapper.class);
    }

}
