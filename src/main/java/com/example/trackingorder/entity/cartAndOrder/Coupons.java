package com.example.trackingorder.entity.cartAndOrder;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.payment.PaymentMethods;
import com.example.trackingorder.status.Status;
import com.example.trackingorder.status.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coupons extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "coupons_code")
    private String Code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "use_limit")
    private Integer useLimit;

    @Column(name = "use_count")
    private Integer useCount;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status")
    private Status status;

    @OneToMany(mappedBy = "coupon")
    private List<Orders> orders ;
}
