package com.example.devteria.mapper;

import com.example.devteria.dtos.request.UserCreationRequest;
import com.example.devteria.dtos.request.UserUpdateRequest;
import com.example.devteria.dtos.response.UserResponse;
import com.example.devteria.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    @Mapping(source = "firstName", target = "lastName")
    UserResponse toUserResponse(User user);
}
