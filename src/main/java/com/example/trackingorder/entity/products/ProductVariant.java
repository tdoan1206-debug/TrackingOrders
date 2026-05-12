package com.example.trackingorder.entity.products;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.entity.cartAndOrder.OrderItems;
import com.example.trackingorder.entity.tracking.ProductReviews;
import com.example.trackingorder.entity.users.DeliveryAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product_variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "description")
    private String description;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "weight_gram")
    private Long weightGram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Products product ;

    @OneToOne(mappedBy = "productVariant" , fetch = FetchType.LAZY)
    private Inventory inventory ;

    @OneToMany(mappedBy = "productVariant")
    private List<CartItems> cartItemsList ;

    @OneToMany(mappedBy = "productVariant")
    private List<OrderItems> orderItems ;

    @OneToMany(mappedBy = "productVariant")
    private List<ProductReviews> productReviews ;

}
