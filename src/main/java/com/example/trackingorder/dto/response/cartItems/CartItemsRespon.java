package com.example.trackingorder.dto.response.cartItems;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsRespon {
     private Long quantity ;
     private BigDecimal itemSubtotal ;
     private BigDecimal cartTotal ;
}
