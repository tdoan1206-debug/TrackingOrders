package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.request.users.UsersReq;
import com.example.trackingorder.dto.response.user.UserResponse;

public interface UsersService {
    void createUser(UsersReq  usersReq) ;

    UserResponse getUser(String userName) ;
}
