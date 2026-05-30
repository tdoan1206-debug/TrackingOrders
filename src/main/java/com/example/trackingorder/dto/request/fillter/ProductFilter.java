package com.example.trackingorder.dto.request.fillter;

import com.example.trackingorder.status.InventoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductFilter {
    private String keyword;
    private String categoryId;
    private InventoryStatus status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
