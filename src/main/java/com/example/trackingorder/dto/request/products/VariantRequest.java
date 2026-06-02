package com.example.trackingorder.dto.request.products;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VariantRequest {
    @NotBlank(message = "SKU không được trống")
    @Size(max = 100)
    private String sku;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal originalPrice;

    private String color;
    private String size;
    private String description;

    @Min(0)
    private int weightGram;

}
