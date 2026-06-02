package com.example.trackingorder.dto.response.inventory;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class InventoryStatsResponse {
    private BigDecimal totalInventoryValue;
    private long totalProducts;
    private long lowStockCount;
    private long outOfStockCount;
    private long inStockCount;
}
