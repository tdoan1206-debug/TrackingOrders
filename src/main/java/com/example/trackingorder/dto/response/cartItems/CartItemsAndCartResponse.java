package com.example.trackingorder.dto.response.cartItems;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CartItemsAndCartResponse {

    private String cartItemId;
    private String productVariantId;
    private String productVariantName;
    private String sku;
    private Long quantity;
    private BigDecimal unitPrice;
    private Long stockQuantity;
    private String stockStatus;
}
