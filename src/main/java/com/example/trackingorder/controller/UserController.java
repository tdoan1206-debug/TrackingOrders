package com.example.trackingorder.controller;

import com.example.trackingorder.dto.request.users.UsersReq;
import com.example.trackingorder.dto.response.user.UserResponse;
import com.example.trackingorder.repository.UserRepo;
import com.example.trackingorder.service.InterfaceService.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UsersService usersService;

    @PostMapping()
    ResponseEntity<String> createUser(@RequestBody @Valid  UsersReq usersReq){
        usersService.createUser(usersReq) ;
        return ResponseEntity.ok("Đã Tạo useer thành công") ;
    }

    @GetMapping()
    ResponseEntity<UserResponse> getUser(Principal principal){
        UserResponse userResponse = usersService.getUser(principal.getName()) ;
        return ResponseEntity.ok(userResponse) ;
    }
}
