package com.example.trackingorder.dto.response.order;

import com.example.trackingorder.status.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemsResponse {
    private String productName  ;
    private Long quantity ;
    private Long quantityInStock ;
    private BigDecimal productPrice ;
    private String message ;
}
