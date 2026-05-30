package com.example.trackingorder.dto.response.cartItems;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsResponse {

     private Long updateQuantity ;
     private BigDecimal itemSubtotal ;
     private BigDecimal cartTotal ;
}
