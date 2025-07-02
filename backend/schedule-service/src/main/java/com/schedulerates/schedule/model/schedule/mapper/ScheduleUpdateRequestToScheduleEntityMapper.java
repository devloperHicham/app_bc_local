package com.schedulerates.schedule.model.schedule.mapper;

import com.schedulerates.schedule.model.common.mapper.BaseMapper;
import com.schedulerates.schedule.model.schedule.dto.request.ScheduleUpdateRequest;
import com.schedulerates.schedule.model.schedule.entity.ScheduleEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link scheduleUpdateRequestToscheduleEntityMapper}
 * for updating {@link scheduleEntity} using
 * {@link ScheduleUpdateRequest}.
 */
@Mapper
public interface ScheduleUpdateRequestToScheduleEntityMapper extends BaseMapper<ScheduleUpdateRequest, ScheduleEntity> {

    /**
     * Maps fields from ScheduleUpdateRequest to update ScheduleEntity.
     *
     * @param scheduleEntityToBeUpdate The ScheduleEntity object to be updated.
     * @param scheduleUpdateRequest    The ScheduleUpdateRequest object containing
     *                                 updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(ScheduleEntity scheduleEntityToBeUpdate, ScheduleUpdateRequest scheduleUpdateRequest) {
        scheduleEntityToBeUpdate.setPortFromId(String.valueOf(scheduleUpdateRequest.getPortFromId()));
        scheduleEntityToBeUpdate.setPortToId(String.valueOf(scheduleUpdateRequest.getPortToId()));
        scheduleEntityToBeUpdate.setCompanyId(String.valueOf(scheduleUpdateRequest.getCompanyId()));
        scheduleEntityToBeUpdate.setDateDepart(scheduleUpdateRequest.getDateDepart());
        scheduleEntityToBeUpdate.setDateArrive(scheduleUpdateRequest.getDateArrive());
        scheduleEntityToBeUpdate.setTransit(scheduleUpdateRequest.getTransit());
        scheduleEntityToBeUpdate.setVessel(scheduleUpdateRequest.getVessel());
        scheduleEntityToBeUpdate.setRefVoyage(scheduleUpdateRequest.getRefVoyage());
        scheduleEntityToBeUpdate.setServiceName(scheduleUpdateRequest.getServiceName());
    }

    /**
     * Initializes and returns an instance of
     * ScheduleUpdateRequestToScheduleEntityMapper.
     *
     * @return Initialized ScheduleUpdateRequestToScheduleEntityMapper instance.
     */
    static ScheduleUpdateRequestToScheduleEntityMapper initialize() {
        return Mappers.getMapper(ScheduleUpdateRequestToScheduleEntityMapper.class);
    }

}
