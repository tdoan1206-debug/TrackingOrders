package com.example.trackingorder.controller;

import com.example.trackingorder.dto.request.CartItems.CartItemsReq;
import com.example.trackingorder.dto.response.cartItems.CartItemsRespon;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.service.InterfaceService.CartItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cartItems")
@RequiredArgsConstructor
@Validated
public class CartItemsController {
    private final CartItemsService cartItemsService ;

    @PutMapping("{id}")
    ResponseEntity<CartItemsRespon>  updateQuantity(@PathVariable String id ,
                                                    @RequestBody CartItemsReq cartItemsReq
                                                    ){

    }
}
