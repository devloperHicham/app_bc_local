package com.schedulerates.schedule.model.schedule.dto.request;

import com.schedulerates.schedule.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving schedule entries as
 * {@link ScheduleClientPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ScheduleClientPagingRequest extends CustomPagingRequest {
    private String selectedPortFromSchedule;
    private String selectedPortToSchedule;
    private String selectedCompany;
    private String searchOn;
    private String startDateSchedule; 
    private String endDateSchedule; 
    private String weeksAhead;
    private Boolean isCheapest;
    private Boolean isFastest;
    private Boolean isDirect;  
}