package com.example.trackingorder.dto.request.products;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {

    @NotBlank(message = "SKU không được trống")
    @Size(max = 100)
    private String sku;

    @NotNull(message = "Giá bán không được trống")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Min(0)
    private Long quantityInStock;

    private String color;
    private String size;

    @Size(max = 2000)
    private String description;

    @Min(0)
    private int weightGram;
}
