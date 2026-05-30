package com.example.trackingorder.dto.response.coupons;

import com.example.trackingorder.status.Type;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponsResponse {
    private String id ;
    private Type couponType;
    private BigDecimal value ;
}
