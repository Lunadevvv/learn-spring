package com.luna.identity_service.mapper;


import com.luna.identity_service.dto.request.UserCreationRequest;
import com.luna.identity_service.dto.request.UserUpdateRequest;
import com.luna.identity_service.dto.response.UserResponse;
import com.luna.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    //annotation MappingTarget la de xac dinh map thang request vao thang user

    //@Mapping(source = "firstname", target = "lastname") - de map firstname vao lastname neu nhu minh muon config mapping
    UserResponse toUserResponse(User user);
}
