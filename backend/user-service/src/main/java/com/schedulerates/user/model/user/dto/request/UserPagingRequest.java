package com.schedulerates.user.model.user.dto.request;

import com.schedulerates.user.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving user as {@link UserPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserPagingRequest extends CustomPagingRequest {

}