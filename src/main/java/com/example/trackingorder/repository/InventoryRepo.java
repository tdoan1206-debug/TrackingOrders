package com.example.trackingorder.repository;

import com.example.trackingorder.entity.products.Inventory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory,String>, JpaSpecificationExecutor<Inventory> {
    Optional<Inventory> findByProductVariantId(String productVariantId) ;

    @Query("SELECT i from Inventory i join fetch i.productVariant where i.productVariant.id in :variantIds")
    List<Inventory> findByProductVariantIdIn(List<String> variantIds);

    @EntityGraph(attributePaths = {"productVariant"})
    List<Inventory> findAll();

}
