package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.order.OrderDetailResponse;
import com.example.trackingorder.dto.response.order.OrderResponse;
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
public interface OrderMapper {

    @Mapping(source = "user",target = "user")
    @Mapping(source = "address",target = "deliveryAddress")
    @Mapping(source = "paymentMethod",target = "paymentMethod")
    @Mapping(source = "coupon",target = "coupon")
    @Mapping(source = "subTotal",target = "subTotal")
    @Mapping(source = "discountAmount",target = "discountAmount")
    @Mapping(source = "shippingFee",target = "shippingFee")
    @Mapping(source = "grandTotal",target = "grandTotal")
    @Mapping(constant = "PENDING",target = "orderStatus")
    @Mapping(constant = "UNPAID",target = "paymentStatus")
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    Orders toOrders(Users user, DeliveryAddress address, PaymentMethods paymentMethod, Coupons coupon, BigDecimal subTotal,
                    BigDecimal discountAmount, BigDecimal shippingFee, BigDecimal grandTotal);



    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "deliveryAddress.name", target = "userName")
    @Mapping(source = "grandTotal", target = "grandTotal")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "deliveryAddress.province", target = "province")
    @Mapping(source = "deliveryAddress.streetDetail", target = "street_detail")
    @Mapping(source = "orderItems", target = "orderItemsResponseList")
    OrderResponse toOrderResponse(Orders order);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "createdAt", target = "createdAt")

    @Mapping(source = "deliveryAddress.name", target = "receiverName")
    @Mapping(source = "deliveryAddress.phone", target = "receiverPhone")
    @Mapping(expression = "java(order.getDeliveryAddress().getStreetDetail() + \", \" + order.getDeliveryAddress().getProvince())",
            target = "receiverAddress")

    @Mapping(source = "paymentMethod.name", target = "paymentMethod")

    @Mapping(source = "subTotal", target = "subtotal")
    @Mapping(source = "discountAmount", target = "discountAmount")
    @Mapping(source = "shippingFee", target = "shippingFee")
    @Mapping(source = "grandTotal", target = "grandTotal")
    OrderDetailResponse toDto(Orders order);
}
