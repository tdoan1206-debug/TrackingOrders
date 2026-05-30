package com.example.trackingorder.dto.response.order;

import com.example.trackingorder.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderStatusResponse {
    private String orderId;
    private OrderStatus previousStatus;
    private OrderStatus currentStatus;
}
