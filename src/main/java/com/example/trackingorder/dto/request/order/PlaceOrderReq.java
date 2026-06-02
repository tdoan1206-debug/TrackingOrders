package com.example.trackingorder.dto.request.order;

import com.example.trackingorder.status.OrderSource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderReq {
    @NotEmpty(message = "orderSummaryItemRequests không được để trống")
    private List<OrderSummaryItemRequest> orderSummaryItemRequests;

    @NotNull(message = "deliveryAddressId không được null")
    private String deliveryAddressId;

    private OrderSource orderSource ;

    @NotNull(message = "paymentMethodId không được null")
    private String paymentMethodId;

    private String couponCode;
}
