package com.example.trackingorder.dto.request.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyNowRequest {
    @NotBlank(message = "product_variant_id không được để trống")
    private String productVariantId;

    @Min(value = 1, message = "quantity phải >= 1")
    private Long quantity = 1L;

    @NotBlank(message = "delivery_address_id không được để trống")
    private String deliveryAddressId;

    @NotBlank(message = "payment_method_id không được để trống")
    private String paymentMethodId;

    private String couponCode;
}
