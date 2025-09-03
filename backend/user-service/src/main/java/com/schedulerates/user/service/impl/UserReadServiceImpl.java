package com.schedulerates.user.service.impl;

import com.schedulerates.user.exception.UserNotFoundException;
import com.schedulerates.user.model.common.CustomPage;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.request.UserPagingRequest;
import com.schedulerates.user.model.user.entity.UserEntity;
import com.schedulerates.user.model.user.enums.UserStatus;
import com.schedulerates.user.model.user.mapper.ListUserEntityToListUserMapper;
import com.schedulerates.user.model.user.mapper.UserEntityToUserMapper;
import com.schedulerates.user.repository.UserRepository;
import com.schedulerates.user.service.UserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation named {@link UserReadServiceImpl} for reading users.
 */
@Service
@RequiredArgsConstructor
public class UserReadServiceImpl implements UserReadService {

    private final UserRepository userRepository;

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    private final ListUserEntityToListUserMapper listUserEntityToListUserMapper = ListUserEntityToListUserMapper
            .initialize();

    /**
     * Retrieves a user by its unique ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User object corresponding to the given ID.
     * @throws UserNotFoundException If no user with the given ID exists.
     */
    @Override
    public User getUserById(String userId) {

        final UserEntity userEntityFromDB = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("With given userID = " + userId));

        return userEntityToUserMapper.map(userEntityFromDB);
    }

    /**
     * Retrieves all Users.
     *
     * @return A list of all User.
     */
    @Override
    public List<User> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(userEntityToUserMapper::map)
                .toList();
    }

    /**
     * Retrieves a page of users based on the paging request criteria.
     *
     * @param userPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of users that match the paging
     *         criteria.
     * @throws UserNotFoundException If no users are found based on the paging
     *                               criteria.
     */
    @Override
    public CustomPage<User> getUsers(UserPagingRequest userPagingRequest) {
        final Page<UserEntity> userEntityPage = userRepository
                .findAllByUserStatus(UserStatus.ACTIVE, userPagingRequest.toPageable());

        if (userEntityPage.getContent().isEmpty()) {
            throw new UserNotFoundException("Couldn't find any ACTIVE users.");
        }

        final List<User> userDomainModels = listUserEntityToListUserMapper
                .toUserList(userEntityPage.getContent());

        return CustomPage.of(userDomainModels, userEntityPage);
    }

    public List<User> getUsersByEmails(List<String> emails) {
        List<UserEntity> userEntities = userRepository.findByEmailIn(emails);
        return userEntities.stream()
                .map(userEntityToUserMapper::map)
                .collect(Collectors.toList());
    }

}