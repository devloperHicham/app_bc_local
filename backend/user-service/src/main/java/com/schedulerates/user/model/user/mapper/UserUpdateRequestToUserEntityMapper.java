package com.schedulerates.user.model.user.mapper;

import com.schedulerates.user.model.common.mapper.BaseMapper;
import com.schedulerates.user.model.user.dto.request.UserUpdateRequest;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.enums.UserType;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link UserUpdateRequestToUserEntityMapper} for updating {@link UserEntity} using {@link UserUpdateRequest}.
 */
@Mapper
public interface UserUpdateRequestToUserEntityMapper extends BaseMapper<UserUpdateRequest, UserEntity> {

    /**
     * Maps fields from UserUpdateRequest to update UserEntity.
     *
     * @param userEntityToBeUpdate The UserEntity object to be updated.
     * @param userUpdateRequest    The UserUpdateRequest object containing updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(UserEntity userEntityToBeUpdate, UserUpdateRequest userUpdateRequest) {
        
        userEntityToBeUpdate.setFirstName(userUpdateRequest.getFirstName());
        userEntityToBeUpdate.setLastName(userUpdateRequest.getLastName());
        userEntityToBeUpdate.setEmail(userUpdateRequest.getEmail());
        userEntityToBeUpdate.setCompanyName(userUpdateRequest.getCompanyName());
        userEntityToBeUpdate.setUserType(UserType.valueOf(userUpdateRequest.getRole()));
        userEntityToBeUpdate.setPhoneNumber(userUpdateRequest.getPhoneNumber());
    }

    /**
     * Initializes and returns an instance of UserUpdateRequestToUserEntityMapper.
     *
     * @return Initialized UserUpdateRequestToUserEntityMapper instance.
     */
    static UserUpdateRequestToUserEntityMapper initialize() {
        return Mappers.getMapper(UserUpdateRequestToUserEntityMapper.class);
    }

}