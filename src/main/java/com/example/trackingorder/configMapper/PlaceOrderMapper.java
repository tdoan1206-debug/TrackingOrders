package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.order.PlaceOrderResponse;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceOrderMapper {


    @Mapping(source = "id",target = "orderId")

    @Mapping(target = "orderStatus", expression = "java(orders.getOrderStatus().name())"
    )
    @Mapping(target = "grandTotal", source = "grandTotal")
    @Mapping(target = "paymentStatus", expression = "java(orders.getPaymentStatus().name())"
    )
    @Mapping(constant = "Đặt hàng thành công",target = "message")
    PlaceOrderResponse toResponse( Orders orders) ;
}
