package com.example.trackingorder.dto.response.inventory;

import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.status.InventoryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CartItemsAndStatus {
    private String cartItemsName ;
    private InventoryStatus status ;
}
