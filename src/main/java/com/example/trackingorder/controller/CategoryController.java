package com.example.trackingorder.controller;

import com.example.trackingorder.dto.response.category.CategoryDetails;
import com.example.trackingorder.dto.response.category.CategorySummary;
import com.example.trackingorder.service.InterfaceService.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    // API 1: GET /api/categories/featured
    // Trả về danh sách danh mục nổi bật (parent_id IS NULL)
    @GetMapping("/featured")
    public ResponseEntity<List<CategorySummary>> getFeaturedCategories() {
        return ResponseEntity.ok(categoryService.getFeaturedCategories());
    }

    // API 2: GET /api/categories/{id}
    // Trả về chi tiết một danh mục + danh mục con
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetails> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // API 3: GET /api/categories
    // Trả về tất cả danh mục trong database
    @GetMapping
    public ResponseEntity<List<CategorySummary>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
