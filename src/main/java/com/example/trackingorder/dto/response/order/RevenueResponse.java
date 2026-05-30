package com.example.trackingorder.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RevenueResponse {
    private BigDecimal totalRevenue;
}
