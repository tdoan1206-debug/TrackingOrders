package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.cartItems.CartItemsResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemsMapper {

    @Mapping(source = "cartItems.quantity" ,target = "updateQuantity")
    @Mapping(source = "itemSubtotal" ,target = "itemSubtotal")
    @Mapping( source = "cartTotal" ,target = "cartTotal")
    CartItemsResponse toResponse(CartItems cartItems, BigDecimal itemSubtotal, BigDecimal cartTotal);
}
