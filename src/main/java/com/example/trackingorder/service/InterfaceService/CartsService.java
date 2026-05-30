package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.request.cart.AddToCartRequest;
import com.example.trackingorder.dto.response.carts.CartsResponse;
import com.example.trackingorder.entity.cartAndOrder.Carts;

public interface CartsService {
    CartsResponse getMyCart(String userName) ;
    void addToCart(String userName , AddToCartRequest request) ;
}
