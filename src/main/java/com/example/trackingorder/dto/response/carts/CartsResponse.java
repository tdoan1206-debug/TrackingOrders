package com.example.trackingorder.dto.response.carts;

import com.example.trackingorder.dto.response.cartItems.CartItemsAndCartResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class CartsResponse {

    private String cartId;
    private String  userId;
    private List<CartItemsAndCartResponse> items;
    private String validationMessage;
}
