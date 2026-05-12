package com.example.trackingorder.entity.cartAndOrder;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.products.ProductVariant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(
        name = "cartItems",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cart_id", "product_detail_id"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItems extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Carts cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

}
