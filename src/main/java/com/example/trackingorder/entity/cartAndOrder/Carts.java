package com.example.trackingorder.entity.cartAndOrder;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.status.CartStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carts extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status" )
    private CartStatus status;

    @Column(name = "sub_total" )
    private BigDecimal subTotal;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user ;

    @OneToMany(mappedBy = "cart")
    private List<CartItems> cartItemsList ;

}
