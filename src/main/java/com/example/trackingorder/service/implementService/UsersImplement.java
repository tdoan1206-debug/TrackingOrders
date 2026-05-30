package com.example.trackingorder.service.implementService;

import com.example.trackingorder.configMapper.UserMapper;
import com.example.trackingorder.dto.request.users.UsersReq;
import com.example.trackingorder.dto.response.user.UserResponse;
import com.example.trackingorder.entity.cartAndOrder.Carts;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.exception.ResponseStatusException;
import com.example.trackingorder.repository.CartsRepo;
import com.example.trackingorder.repository.UserRepo;
import com.example.trackingorder.service.InterfaceService.UsersService;
import com.example.trackingorder.status.CartStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor

public class UsersImplement implements UsersService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CartsRepo cartsRepo;

    @Override
    @Transactional
    public void createUser(UsersReq usersReq) {
        Users users = userMapper.toUsers(usersReq);
        users.setPassWord(passwordEncoder.encode(usersReq.getPassWord()));

        Carts newCart = new Carts();
        newCart.setUser(users);
        newCart.setStatus(CartStatus.ACTIVE);
        newCart.setSubTotal(BigDecimal.ZERO);
        newCart.setCreatedBy(usersReq.getUserName());


        cartsRepo.save(newCart);
         userRepo.save(users);
    }

    @Override
    public UserResponse getUser(String userName) {
        Users users = userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thấy user"));
        UserResponse userResponse = userMapper.toUsersResponse(users);

        return userResponse;
    }
}
