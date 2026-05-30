package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.product.ProductFeaturedItem;
import com.example.trackingorder.entity.products.ProductVariant;
import com.example.trackingorder.entity.products.Products;
import com.example.trackingorder.status.InventoryStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ProductFeaturedMapper {


    @Mapping(target = "price", expression = "java(getPrice(product))")
    @Mapping(target = "originalPrice", expression = "java(getOriginalPrice(product))")
    @Mapping(target = "stockStatus", expression = "java(getStockStatus(product))")
    ProductFeaturedItem toProductFeaturedItem(Products product);

    default BigDecimal getPrice(Products product) {
        ProductVariant variant = product.getProductVariants().getFirst();
        return variant.getPrice();
    }

    default BigDecimal getOriginalPrice(Products product) {
        ProductVariant variant = product.getProductVariants().getFirst();
        return variant.getOriginalPrice();
    }

    default InventoryStatus getStockStatus(Products product) {
        ProductVariant variant = product.getProductVariants().getFirst();

        if (variant.getInventory() == null) {
            return null;
        }
        return variant.getInventory().getStatus();
    }
}
