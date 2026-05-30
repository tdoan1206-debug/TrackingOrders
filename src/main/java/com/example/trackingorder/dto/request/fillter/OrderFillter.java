package com.example.trackingorder.dto.request.fillter;

import com.example.trackingorder.status.OrderStatus;
import com.example.trackingorder.status.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderFillter {
    private String orderId;
    private BigDecimal minTotal;
    private BigDecimal maxTotal;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
