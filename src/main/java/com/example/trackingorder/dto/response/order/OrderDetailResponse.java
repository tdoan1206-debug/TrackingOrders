package com.example.trackingorder.dto.response.order;

import com.example.trackingorder.status.OrderStatus;
import com.example.trackingorder.status.PaymentStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDetailResponse {

    // ===== Order info =====
    private String orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;

    // ===== Receiver =====
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;

    // ===== Payment =====
    private String paymentMethod;


    private List<OrderItem> items;

    // ===== Pricing =====
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private BigDecimal grandTotal;

    // ===== Shipment =====
    private String trackingNumber;
}

