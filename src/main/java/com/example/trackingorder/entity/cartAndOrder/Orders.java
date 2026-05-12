package com.example.trackingorder.entity.cartAndOrder;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.payment.PaymentMethods;
import com.example.trackingorder.entity.payment.PaymentTransactions;
import com.example.trackingorder.entity.payment.Shipments;
import com.example.trackingorder.entity.tracking.NotificationQueue;
import com.example.trackingorder.entity.tracking.ProductReviews;
import com.example.trackingorder.entity.tracking.Returns;
import com.example.trackingorder.entity.tracking.TrackingLogs;
import com.example.trackingorder.entity.users.DeliveryAddress;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.status.OrderStatus;
import com.example.trackingorder.status.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "grand_total")
    private BigDecimal grandTotal;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress deliveryAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupons coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethods paymentMethod;


    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems ;

    @OneToMany(mappedBy = "order")
    private List<PaymentTransactions> paymentTransactions ;

    @OneToMany(mappedBy = "order")
    private List<Shipments> shipments ;

    @OneToMany(mappedBy = "orders")
    private List<TrackingLogs> trackingLogs ;

    @OneToMany(mappedBy = "order")
    private List<NotificationQueue> notificationQueues ;

    @OneToMany(mappedBy = "order")
    private List<ProductReviews> productReviews ;

    @OneToMany(mappedBy = "order")
    private List<Returns> returns ;
}
