package com.example.trackingorder.entity.payment;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.users.DeliveryAddress;
import com.example.trackingorder.status.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethods extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "payment_name")
    private String name;

    @Column(name = "payment_type")
    private PaymentType type;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Orders> orders ;
}
