package com.schedulerates.schedule.model.schedule.mapper;

import com.schedulerates.schedule.model.common.mapper.BaseMapper;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleCreateRequest;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleUpdateRequest;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ScheduleCreateRequestToScheduleEntityMapper}
 * for converting {@link ScheduleCreateRequest} to {@link ScheduleEntity}.
 */
@Mapper
public interface ScheduleCreateRequestToScheduleEntityMapper extends BaseMapper<ScheduleUpdateRequest, ScheduleEntity> {

    /**
     * Maps ScheduleCreateRequest to ScheduleEntity for saving purposes.
     *
     * @param scheduleCreateRequest The ScheduleCreateRequest object to map.
     * @return ScheduleEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default ScheduleEntity mapForSaving(ScheduleCreateRequest scheduleCreateRequest) {
        return ScheduleEntity.builder()
                /* .portFromId(String.valueOf(scheduleCreateRequest.getPortFromId()))
                .portToId(String.valueOf(scheduleCreateRequest.getPortToId()))
                .companyId(String.valueOf(scheduleCreateRequest.getCompanyId()))
                .dateDepart(scheduleCreateRequest.getDateDepart())
                .dateArrive(scheduleCreateRequest.getDateArrive())
                .transit(scheduleCreateRequest.getTransit())
                .vessel(scheduleCreateRequest.getVessel())
                .refVoyage(scheduleCreateRequest.getRefVoyage())
                .serviceName(scheduleCreateRequest.getServiceName())
                // Initialize name fields with empty strings
                .portFromName("")
                .portToName("")*/
                .companyName("")
                .build();
    }

    /**
     * Initializes and returns an instance of
     * ScheduleCreateRequestToScheduleEntityMapper.
     *
     * @return Initialized ScheduleCreateRequestToScheduleEntityMapper instance.
     */
    static ScheduleCreateRequestToScheduleEntityMapper initialize() {
        return Mappers.getMapper(ScheduleCreateRequestToScheduleEntityMapper.class);
    }

}
