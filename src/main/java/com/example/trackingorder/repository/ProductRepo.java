package com.example.trackingorder.repository;

import com.example.trackingorder.entity.products.Inventory;
import com.example.trackingorder.entity.products.ProductVariant;
import com.example.trackingorder.entity.products.Products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Products,String>, JpaSpecificationExecutor<Products> {
    List<Products> findByNameContainingIgnoreCase(String keyWord) ;

    @EntityGraph(attributePaths = {"productVariants", "productVariants.inventory"})
    Page<Products> findAll(Pageable pageable);

}
