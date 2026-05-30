package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.response.inventory.InventoryResponse;

public interface InventoryService {
    InventoryResponse refreshInventory(String userName);
}
