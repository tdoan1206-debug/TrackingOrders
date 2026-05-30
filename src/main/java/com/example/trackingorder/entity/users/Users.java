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
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table( name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DeliveryAddress> addresses ;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Carts carts ;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Orders> orders ;

    @OneToMany(mappedBy = "shipper", fetch = FetchType.LAZY)
    private List<Shipments> shipments ;

    @OneToMany(mappedBy = "updater", fetch = FetchType.LAZY)
    private List<TrackingLogs> trackingLogs ;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<NotificationQueue> notificationQueues ;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ProductReviews> productReviews ;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Returns> returns ;
}
