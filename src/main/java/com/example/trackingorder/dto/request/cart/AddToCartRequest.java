package com.example.trackingorder.dto.request.cart;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartRequest {

    @NotBlank(message = "product_variant_id không được để trống")
    private String productVariantId;

    @Min(value = 1, message = "quantity phải >= 1")
    private Long quantity = 1L;
}