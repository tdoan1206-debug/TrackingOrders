package com.example.trackingorder.repository;

import com.example.trackingorder.entity.products.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory,Long>, JpaSpecificationExecutor<Inventory> {
    Optional<Inventory> findByProductVariantId(String productVariantId) ;
}
