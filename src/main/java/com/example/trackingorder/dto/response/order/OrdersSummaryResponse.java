package com.example.trackingorder.dto.response.order;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class OrdersSummaryResponse {
  private BigDecimal subTotal ;
  private BigDecimal valueCoupons ;
  private BigDecimal shippingFee ;
  private BigDecimal totalAmount ;
}
