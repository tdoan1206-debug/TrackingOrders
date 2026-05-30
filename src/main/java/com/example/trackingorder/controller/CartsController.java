package com.example.trackingorder.controller;

import com.example.trackingorder.dto.request.cart.AddToCartRequest;
import com.example.trackingorder.dto.response.carts.CartsResponse;
import com.example.trackingorder.entity.cartAndOrder.Carts;
import com.example.trackingorder.service.InterfaceService.CartsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
@Validated
public class CartsController {
    private final CartsService cartsService;

    @GetMapping("/my-cart")
    ResponseEntity<CartsResponse> getMyCarts(Principal principal) {
        CartsResponse carts = cartsService.getMyCart(principal.getName()) ;
        return ResponseEntity.ok(carts) ;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Buyer')")
    ResponseEntity<String> addToCart(Principal principal, @Valid @RequestBody AddToCartRequest request) {
        cartsService.addToCart(principal.getName(), request) ;
        return ResponseEntity.ok("Đã Thêm Thành Công") ;
    }


}
