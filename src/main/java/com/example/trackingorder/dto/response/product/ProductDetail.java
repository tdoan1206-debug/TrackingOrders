package com.example.trackingorder.dto.response.product;

import com.example.trackingorder.status.InventoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetail {

    private String productId;

    private String productName;

    private String categoryName;

    private String variantId;

    private String sku;

    private String color;

    private String size;

    private String description;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private Long quantityInStock;

    private InventoryStatus inventoryStatus;
}
