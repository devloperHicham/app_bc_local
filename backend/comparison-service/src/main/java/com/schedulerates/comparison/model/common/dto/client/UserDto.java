package com.schedulerates.comparison.model.common.dto.client;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
}