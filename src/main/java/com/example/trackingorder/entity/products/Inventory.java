package com.example.trackingorder.entity.products;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.status.InventoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "quantity_in_stock")
    private Long quantityInStock ;

    @Column(name = "reserved_quantity")
    private Long reservedQuantity ;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InventoryStatus status ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    @JsonIgnore
    private ProductVariant productVariant ;
}
