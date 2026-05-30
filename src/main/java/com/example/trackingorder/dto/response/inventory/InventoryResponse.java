package com.example.trackingorder.dto.response.inventory;

import com.example.trackingorder.status.InventoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryResponse {
    private List<CartItemsAndStatus> cartItemsAndStatusList ;
}
