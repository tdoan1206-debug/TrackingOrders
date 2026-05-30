package com.example.trackingorder.dto.response.order;

import com.example.trackingorder.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MyOrderResponse {
    private String id;
    private String orderCode;
    private OrderStatus orderStatus;
    private String paymentMethod;
    private BigDecimal grandTotal;
    private LocalDateTime createdAt;
    private int productCount;
}
