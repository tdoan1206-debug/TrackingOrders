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

import java.util.List;

@Entity
@Table(name = "coupons")
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

    @Column(name = "cart_status" )
    private CartStatus status;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user ;

    @OneToMany(mappedBy = "cart")
    private List<CartItems> cartItemsList ;



}
