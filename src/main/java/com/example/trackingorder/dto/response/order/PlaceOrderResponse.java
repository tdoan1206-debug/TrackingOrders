package com.example.trackingorder.dto.response.order;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlaceOrderResponse {

    private String orderId;
    private BigDecimal grandTotal;
    private String orderStatus;
    private String paymentStatus;
    private String message;
}
