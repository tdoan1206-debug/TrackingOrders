package com.example.trackingorder.repository;

import com.example.trackingorder.entity.products.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Categories,String>, JpaSpecificationExecutor<Categories> {

    // API 1: Danh mục nổi bật = danh mục gốc (parent_id IS NULL)
    @Query("SELECT c FROM Categories c WHERE c.parent.id IS NULL")
    List<Categories> findFeaturedCategories();

    Optional<Categories> findById(String id);

    List<Categories> findAll();
}
