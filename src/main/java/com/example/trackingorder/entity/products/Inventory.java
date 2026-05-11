package com.example.trackingorder.entity.products;

import com.example.trackingorder.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock ;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity ;

    @OneToOne()
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant ;
}
