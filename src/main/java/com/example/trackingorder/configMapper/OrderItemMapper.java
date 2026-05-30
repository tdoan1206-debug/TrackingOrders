package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.order.OrderItem;
import com.example.trackingorder.dto.response.order.OrderItemsResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.entity.cartAndOrder.OrderItems;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.products.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "order",target = "order")
    @Mapping(source = "item.productVariant",target = "productVariant")
    @Mapping(source = "item.quantity",target = "quantity")
    @Mapping(source = "item.productVariant.price",target = "price")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    OrderItems toOrderItems(Orders order, CartItems item) ;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", source = "order")
    @Mapping(target = "productVariant", source = "productVariant")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    OrderItems toOrderItem2(Orders order, ProductVariant productVariant, Long quantity, BigDecimal price);

    @Mapping(source = "productVariant.product.name", target = "productName")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "productVariant.inventory.quantityInStock", target = "quantityInStock")
    @Mapping(source = "price", target = "productPrice")
    OrderItemsResponse toOrderItemsResponse(OrderItems item);


    @Mapping(source = "productVariant.product.name", target = "productName")
    @Mapping(source = "productVariant.sku", target = "sku")
    @Mapping(expression = "java(item.getProductVariant().getColor() + \" - \" + item.getProductVariant().getSize())", target = "variantInfo")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "unitPrice")
    OrderItem toDto(OrderItems item);
}
