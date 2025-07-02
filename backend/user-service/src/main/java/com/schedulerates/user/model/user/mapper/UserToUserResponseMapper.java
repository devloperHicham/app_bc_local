package com.schedulerates.user.model.user.mapper;

import com.schedulerates.user.model.common.mapper.BaseMapper;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link UserToUserResponseMapper} for converting {@link User} to {@link UserResponse}.
 */
@Mapper
public interface UserToUserResponseMapper extends BaseMapper<User, UserResponse> {

    /**
     * Maps User to UserResponse.
     *
     * @param source The User object to map.
     * @return UserResponse object containing mapped data.
     */
    @Override
    UserResponse map(User source);

    /**
     * Initializes and returns an instance of UserToUserResponseMapper.
     *
     * @return Initialized UserToUserResponseMapper instance.
     */
    static UserToUserResponseMapper initialize() {
        return Mappers.getMapper(UserToUserResponseMapper.class);
    }

}