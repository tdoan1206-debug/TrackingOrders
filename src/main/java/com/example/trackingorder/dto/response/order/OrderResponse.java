package com.example.trackingorder.dto.response.order;

import com.example.trackingorder.entity.users.DeliveryAddress;
import com.example.trackingorder.status.OrderStatus;
import com.example.trackingorder.status.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private String orderId ;
    private String userName ;
    private BigDecimal grandTotal ;
    private OrderStatus orderStatus ;
    private PaymentStatus paymentStatus ;
    private String  province;
    private String  street_detail;
    private List<OrderItemsResponse> orderItemsResponseList ;
}
