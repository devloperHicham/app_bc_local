package com.schedulerates.user.model.user.mapper;

import com.schedulerates.user.model.common.CustomPage;
import com.schedulerates.user.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.user.model.user.User;
import com.schedulerates.user.model.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Product>} to {@link CustomPagingResponse<ProductResponse>}.
 */
@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    UserToUserResponseMapper userToUserResponseMapper = Mappers.getMapper(UserToUserResponseMapper.class);

    /**
     * Converts a CustomPage<Product> object to CustomPagingResponse<ProductResponse>.
     *
     * @param userPage The CustomPage<Product> object to convert.
     * @return CustomPagingResponse<ProductResponse> object containing mapped data.
     */
    default CustomPagingResponse<UserResponse> toPagingResponse(CustomPage<User> userPage) {

        if (userPage == null) {
            return null;
        }

        return CustomPagingResponse.<UserResponse>builder()
                .content(toUserResponseList(userPage.getContent()))
                .totalElementCount(userPage.getTotalElementCount())
                .totalPageCount(userPage.getTotalPageCount())
                .pageNumber(userPage.getPageNumber())
                .pageSize(userPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of User objects to a list of UserResponse objects.
     *
     * @param users The list of User objects to convert.
     * @return List of UserResponse objects containing mapped data.
     */
    default List<UserResponse> toUserResponseList(List<User> users) {

        if (users == null) {
            return null;
        }

        return users.stream()
                .map(userToUserResponseMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of CustomPageToCustomPagingResponseMapper.
     *
     * @return Initialized CustomPageToCustomPagingResponseMapper instance.
     */
    static CustomPageToCustomPagingResponseMapper initialize() {
        return Mappers.getMapper(CustomPageToCustomPagingResponseMapper.class);
    }

}