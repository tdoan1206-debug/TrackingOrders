package com.example.trackingorder.configMapper;

import com.example.trackingorder.entity.cartAndOrder.Coupons;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.payment.PaymentMethods;
import com.example.trackingorder.entity.users.DeliveryAddress;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.status.OrderStatus;
import com.example.trackingorder.status.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderBuyNowMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "coupon", source = "coupon")
    @Mapping(target = "orderStatus", source = "orderStatus")
    @Mapping(target = "subTotal", source = "subTotal")
    @Mapping(target = "discountAmount", source = "discountAmount")
    @Mapping(target = "shippingFee", source = "shippingFee")
    @Mapping(target = "grandTotal", source = "grandTotal")
    @Mapping(target = "paymentStatus", source = "paymentStatus")
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Orders toOrder(Users user, DeliveryAddress deliveryAddress, PaymentMethods paymentMethod, Coupons coupon,
                   OrderStatus orderStatus, BigDecimal subTotal,BigDecimal discountAmount, BigDecimal shippingFee,
                   BigDecimal grandTotal, PaymentStatus paymentStatus);
}
