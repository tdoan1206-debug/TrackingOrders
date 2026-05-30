package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.cartItems.CartItemsAndCartResponse;
import com.example.trackingorder.dto.response.carts.CartsResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.entity.cartAndOrder.Carts;
import com.example.trackingorder.status.InventoryStatus;
import com.zaxxer.hikari.HikariDataSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartsMapper {
    @Mapping(source = "id",target = "cartId") // cartsResponse.setCartId(carts.getId());
    @Mapping(source = "user.id", target = "userId") //  cartsResponse.setUserId(carts.getUser().getId());
    @Mapping(source = "cartItemsList", target = "items") // cartsResponse.setItems(itemResponses);
    CartsResponse toResponse(Carts carts);

    // Vì List<CartItems> -> List<CartItemsAndCartResponse>
    @Mapping(source = "id", target = "cartItemId")   //cartResponse.setCartItemId(items.getId());
    @Mapping(source = "productVariant.id", target = "productVariantId") //    cartResponse.setProductVariantId(productVariant.getId());
    @Mapping(source = "productVariant.product.name", target = "productVariantName") // cartResponse.setProductVariantName(productVariant.getProduct().getName());
    @Mapping(source = "productVariant.sku", target = "sku") //      cartResponse.setSku(productVariant.getSku());
    @Mapping(source = "quantity",target = "quantity")
    @Mapping(source = "productVariant.price", target = "unitPrice") //   cartResponse.setUnitPrice(productVariant.getPrice());
    @Mapping(source = "productVariant.inventory.quantityInStock", target = "stockQuantity") //cartResponse.setStockQuantity(stockQuantity);
    @Mapping(target = "stockStatus", expression = "java(getStockStatus(items))") //
    CartItemsAndCartResponse toItemResponse(CartItems items);

    List<CartItemsAndCartResponse>  toItemResponses (List<CartItems> items) ;
    default String getStockStatus(CartItems items) {

        Long stockQuantity =
                items.getProductVariant()
                        .getInventory()
                        .getQuantityInStock();

        if (stockQuantity <= 0) {
            return InventoryStatus.OUT_OF_STOCK.name();
        }
        if (stockQuantity < 5) {
            return InventoryStatus.LIMIDTED_STOCK.name()
                    + " (Còn " + stockQuantity + " sản phẩm)";
        }
        return InventoryStatus.IN_STOCK.name();
    }

}
