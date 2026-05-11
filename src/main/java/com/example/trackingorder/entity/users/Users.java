package com.example.trackingorder.entity.users;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.cartAndOrder.Carts;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.payment.Shipments;
import com.example.trackingorder.entity.tracking.NotificationQueue;
import com.example.trackingorder.entity.tracking.ProductReviews;
import com.example.trackingorder.entity.tracking.Returns;
import com.example.trackingorder.entity.tracking.TrackingLogs;
import com.example.trackingorder.status.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table( name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "email")
    private String email ;

    @Column(name = "password")
    private String passWord ;

    @Column(name = "full_name")
    private String fullName ;

    @Column(name = "user_name")
    private String userName ;

    @Column(name = "phone")
    private String phone ;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<DeliveryAddress> addresses ;

    @OneToOne(mappedBy = "user")
    private Carts carts ;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders ;

    @OneToMany(mappedBy = "shipper")
    private List<Shipments> shipments ;

    @OneToMany(mappedBy = "updater")
    private List<TrackingLogs> trackingLogs ;

    @OneToMany(mappedBy = "user")
    private List<NotificationQueue> notificationQueues ;

    @OneToMany(mappedBy = "user")
    private List<ProductReviews> productReviews ;

    @OneToMany(mappedBy = "user")
    private List<Returns> returns ;
}
