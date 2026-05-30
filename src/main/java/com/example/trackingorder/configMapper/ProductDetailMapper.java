package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.product.ProductDetail;
import com.example.trackingorder.entity.products.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "categoryName", source = "product.category.name")

    @Mapping(target = "variantId", source = "id")
    @Mapping(target = "sku", source = "sku")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "description", source = "description")

    @Mapping(target = "price", source = "price")
    @Mapping(target = "originalPrice", source = "originalPrice")

    @Mapping(target = "quantityInStock", source = "inventory.quantityInStock")
    @Mapping(target = "inventoryStatus", source = "inventory.status")
    ProductDetail toProductDetail(ProductVariant variant);
}