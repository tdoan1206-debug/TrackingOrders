package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.order.OrdersSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderSummaryMapper {


    @Mapping(source = "subTotal",target = "subTotal")
    @Mapping(source = "discountValue",target = "valueCoupons")
    @Mapping(source = "shippingFee",target = "shippingFee")
    @Mapping(source = "totalAmount",target = "totalAmount")
    OrdersSummaryResponse toResponse (BigDecimal subTotal, BigDecimal discountValue,BigDecimal shippingFee ,BigDecimal totalAmount) ;
}
