package com.example.trackingorder.spec;

import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.products.Products;
import com.example.trackingorder.status.InventoryStatus;
import com.example.trackingorder.status.OrderStatus;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Products> likeKeyWord(String keyWord ) {
        return new Specification<Products>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if(keyWord == null || keyWord.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.like(root.get("name") , "%" + keyWord + "%") ;
            }
        };
    }

    public static Specification<Products> equalCategoryId(String categoryId) {
        return new Specification<Products>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if(categoryId == null || categoryId.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.equal(root.get("category").get("id") ,categoryId) ;
            }
        };
    }

    public static Specification<Products> equalInventoryStatus(InventoryStatus status) {
        return new Specification<Products>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {


                if (status == null) return criteriaBuilder.conjunction();
                Join<Object, Object> variants = root.join("productVariants");
                return criteriaBuilder.equal(root.get("inventory").get("status") , status) ;
            }
        };
    }

    public static Specification<Products> minPrice(BigDecimal minPrice) {
        return new Specification<Products>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (minPrice == null) return criteriaBuilder.conjunction();

                Join<Object, Object> variants = root.join("productVariants");
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price") , minPrice ) ;
            }
        };
    }

    public static Specification<Products> maxPrice(BigDecimal maxPrice)  {
        return new Specification<Products>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (maxPrice == null) return criteriaBuilder.conjunction();
                Join<Object, Object> variants = root.join("productVariants");
                return criteriaBuilder.lessThanOrEqualTo(root.get("price") , maxPrice ) ;
            }
        };
    }




}
