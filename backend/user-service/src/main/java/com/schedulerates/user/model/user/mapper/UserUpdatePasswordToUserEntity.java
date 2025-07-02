package com.schedulerates.user.model.user.mapper;

import com.schedulerates.user.model.common.mapper.BaseMapper;
import com.schedulerates.user.model.user.dto.request.UserUpdatePasswordRequest;
import com.schedulerates.user.model.user.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link UserUpdatePasswordRequestToUserEntityMapper} for updating {@link UserEntity} using {@link UserUpdatePasswordRequest}.
 */
@Mapper
public interface UserUpdatePasswordToUserEntity extends BaseMapper<UserUpdatePasswordRequest, UserEntity> {

    /**
     * Maps fields from UserUpdateRequest to update UserEntity.
     *
     * @param userEntityToBeUpdate The UserEntity object to be updated.
     * @param userUpdateRequest    The UserUpdateRequest object containing updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(UserEntity userEntityToBeUpdate, UserUpdatePasswordRequest userUpdatePasswordRequest) {

        userEntityToBeUpdate.setPassword(userUpdatePasswordRequest.getNewPassword());
    }

    /**
     * Initializes and returns an instance of UserUpdatePasswordToUserEntity.
     *
     * @return Initialized UserUpdatePasswordToUserEntity instance.
     */
    static UserUpdatePasswordToUserEntity initialize() {
        return Mappers.getMapper(UserUpdatePasswordToUserEntity.class);
    }

}