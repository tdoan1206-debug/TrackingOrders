package com.example.trackingorder.service.implementService;

import com.example.trackingorder.dto.response.category.CategoryDetails;
import com.example.trackingorder.dto.response.category.CategorySummary;
import com.example.trackingorder.entity.products.Categories;
import com.example.trackingorder.repository.CategoryRepo;
import com.example.trackingorder.service.InterfaceService.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImplement  implements CategoryService {

    private final CategoryRepo categoryRepo ;

    @Override
    public List<CategorySummary> getFeaturedCategories() {
        List<Categories> categories = categoryRepo.findFeaturedCategories() ;
        List<CategorySummary> result = new ArrayList<>();

        for(Categories item : categories){
            CategorySummary categorySummary = new CategorySummary() ;
            categorySummary.setId(item.getId());
            categorySummary.setName(item.getName());

            result.add(categorySummary) ;
         }

        return result;
    }


    @Override
    public CategoryDetails getCategoryById(String id) {
        // Tìm category theo id
        Categories category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục " ));

        List<CategorySummary> children = new ArrayList<>();

        if (category.getChildren() != null) {

            for (Categories child : category.getChildren()) {

                CategorySummary categorySummary = new CategorySummary() ;
                categorySummary.setId(child.getId());
                categorySummary.setName(child.getName());

                children.add(categorySummary);
            }
        }
        String parentId = null;


        CategoryDetails categoryDetails = new CategoryDetails() ;

        categoryDetails.setId(category.getId());
        categoryDetails.setName(category.getName());
        if (category.getParent() != null) {
            parentId = category.getParent().getId();
        }
        categoryDetails.setParentId(parentId);
        categoryDetails.setChildren(children);

        return categoryDetails ;
    }

    @Override
    public List<CategorySummary> getAllCategories() {

        List<Categories> categories = categoryRepo.findAll();

        List<CategorySummary> result = new ArrayList<>();

        // Duyệt từng category
        for (Categories category : categories) {

            CategorySummary categorySummary = new CategorySummary() ;
            categorySummary.setId(category.getId());
            categorySummary.setName(category.getName());

            result.add(categorySummary);
        }

        return result;
    }
}
