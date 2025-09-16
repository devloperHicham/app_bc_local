package com.schedulerates.user.model.user.mapper;

import com.schedulerates.user.model.common.mapper.BaseMapper;
import com.schedulerates.user.model.user.dto.request.RegisterRequest;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.enums.UserType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link RegisterRequest} and
 * {@link UserEntity}.
 * This mapper handles the transformation of user registration request data into
 * a user entity
 * for persistence in the database.
 */
@Mapper
public interface RegisterRequestToUserEntityMapper extends BaseMapper<RegisterRequest, UserEntity> {

    /**
     * Maps a {@link RegisterRequest} to a {@link UserEntity} for saving.
     * This method maps the user's registration request to a {@link UserEntity} with
     * appropriate
     * user type based on the role specified in the request.
     *
     * @param userRegisterRequest the registration request containing user details
     * @return a {@link UserEntity} with mapped values
     */
    @Named("mapForSaving")
    default UserEntity mapForSaving(RegisterRequest userRegisterRequest) {

        UserType userType;

        if ("admin".equalsIgnoreCase(userRegisterRequest.getRole())) {
            userType = UserType.ADMIN;
        } else if ("client".equalsIgnoreCase(userRegisterRequest.getRole())) {
            userType = UserType.CLIENT;
        } else {
            userType = UserType.USER; // default
        }
        return UserEntity.builder()
                .email(userRegisterRequest.getEmail())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .userType(userType)
                .build();
    }

    /**
     * Initializes the {@link RegisterRequestToUserEntityMapper} mapper.
     *
     * @return a new instance of {@link RegisterRequestToUserEntityMapper}
     */
    static RegisterRequestToUserEntityMapper initialize() {
        return Mappers.getMapper(RegisterRequestToUserEntityMapper.class);
    }

}