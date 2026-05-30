package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.request.users.UsersReq;
import com.example.trackingorder.dto.response.user.UserResponse;
import com.example.trackingorder.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toUsers(UsersReq usersReq) ;
    UserResponse toUsersResponse(Users users) ;
}
