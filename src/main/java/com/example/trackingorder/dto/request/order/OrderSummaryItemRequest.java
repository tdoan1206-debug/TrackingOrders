package com.example.trackingorder.dto.request.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryItemRequest {
    private String productId ;
    private Long quantity ;
}
