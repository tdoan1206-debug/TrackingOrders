package com.example.trackingorder.controller;

import com.example.trackingorder.dto.request.cartItems.CartItemsReq;
import com.example.trackingorder.dto.response.cartItems.CartItemsResponse;
import com.example.trackingorder.service.InterfaceService.CartItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cartItems")
@RequiredArgsConstructor
@Validated
public class CartItemsController {
    private final CartItemsService cartItemsService;

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('Buyer')")
    ResponseEntity<CartItemsResponse> updateQuantity(@PathVariable String id, @RequestBody CartItemsReq cartItemsReq)
    {
        CartItemsResponse cartItemsRespon = cartItemsService.updateQuantity(id, cartItemsReq);
        return ResponseEntity.ok(cartItemsRespon);
    }


}
