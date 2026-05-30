package com.example.trackingorder.dto.response.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private String productName;
    private String sku;
    private String variantInfo; // ví dụ: "Red - XL"
    private Long quantity;
    private BigDecimal unitPrice;
}
