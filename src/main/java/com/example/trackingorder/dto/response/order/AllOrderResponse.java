package com.example.trackingorder.dto.response.order;

import com.example.trackingorder.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class AllOrderResponse {
    private String orderId;
    private String customerName;
    private BigDecimal grandTotal;
    private OrderStatus status;
    private String carriersName ;
    private LocalDateTime createdAt;
}
