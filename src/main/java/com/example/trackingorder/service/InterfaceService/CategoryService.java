package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.response.category.CategoryDetails;
import com.example.trackingorder.dto.response.category.CategorySummary;

import java.util.List;

public interface CategoryService {
    List<CategorySummary> getFeaturedCategories();

    CategoryDetails getCategoryById(String id);

    List<CategorySummary> getAllCategories();
}
