package com.example.trackingorder.dto.request.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderReq {
    @NotEmpty(message = "cartItemIds không được để trống")
    private List<String> cartItemIds;

    @NotNull(message = "deliveryAddressId không được null")
    private String deliveryAddressId;

    @NotNull(message = "paymentMethodId không được null")
    private String paymentMethodId;

    private String couponCode;
}
