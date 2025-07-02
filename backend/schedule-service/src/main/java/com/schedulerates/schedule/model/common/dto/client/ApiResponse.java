package com.schedulerates.schedule.model.common.dto.client;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String time;
    private String httpStatus;
    private boolean isSuccess;
    private T response;
}