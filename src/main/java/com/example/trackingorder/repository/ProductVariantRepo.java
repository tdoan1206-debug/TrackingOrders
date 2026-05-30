package com.example.trackingorder.repository;

import com.example.trackingorder.entity.products.ProductVariant;
import com.example.trackingorder.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductVariantRepo extends JpaRepository<ProductVariant,String>, JpaSpecificationExecutor<ProductVariant>  {

    @Query("""
            select pv
            from ProductVariant pv
            join fetch pv.product p
            join fetch p.category
            join fetch pv.inventory
            where p.id = :productId
            """)
    Optional<ProductVariant> findDetailByProductId(String productId);
}
