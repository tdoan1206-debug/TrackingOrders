package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.request.cartItems.CartItemsReq;
import com.example.trackingorder.dto.response.cartItems.CartItemsResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;

public interface CartItemsService {
    CartItemsResponse updateQuantity(String cartItemsId, CartItemsReq cartItemsReq);
    CartItems findById(String cartItemsId) ;

}
