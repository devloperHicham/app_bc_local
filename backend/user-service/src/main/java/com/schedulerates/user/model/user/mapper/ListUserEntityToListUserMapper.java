package com.schedulerates.user.model.user.mapper;

import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListUserEntityToListUserMapper} for converting {@link List<UserEntity>} to {@link List<User>}.
 */
@Mapper
public interface ListUserEntityToListUserMapper {

    UserEntityToUserMapper userEntityToUserMapper = Mappers.getMapper(UserEntityToUserMapper.class);

    /**
     * Converts a list of UserEntity objects to a list of User objects.
     *
     * @param userEntities The list of UserEntity objects to convert.
     * @return List of User objects containing mapped data.
     */
    default List<User> toUserList(List<UserEntity> userEntities) {

        if (userEntities == null) {
            return null;
        }

        return userEntities.stream()
                .map(userEntityToUserMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListUserEntityToListUserMapper.
     *
     * @return Initialized ListUserEntityToListUserMapper instance.
     */
    static ListUserEntityToListUserMapper initialize() {
        return Mappers.getMapper(ListUserEntityToListUserMapper.class);
    }

}