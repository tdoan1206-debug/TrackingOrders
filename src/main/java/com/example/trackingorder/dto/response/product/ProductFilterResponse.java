package com.example.trackingorder.dto.response.product;

import com.example.trackingorder.status.InventoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductFilterResponse {
    private String productVariantId;
    private String productName;
    private String sku;
    private String categoryName;
    private BigDecimal price;
    private int quantityInStock;
    private InventoryStatus status;
}
