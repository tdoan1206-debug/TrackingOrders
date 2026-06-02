package com.example.trackingorder.dto.request.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderSummaryRequest {
    List<OrderSummaryItemRequest> orderSummaryItemRequests ;
    private String promotionCode ;
}
