package com.example.trackingorder.dto.response.product;

import com.example.trackingorder.status.InventoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductFeaturedItem {

    private String id;
    private String name;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private InventoryStatus stockStatus;
}
