package com.example.trackingorder.dto.request.order;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderSummaryReq {
    private String couponCode ;
    private List<String> cartItemsId ;
}
