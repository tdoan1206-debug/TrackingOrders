package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.response.inventory.InventoryResponse;
import com.example.trackingorder.dto.response.inventory.InventoryStatsResponse;

public interface InventoryService {
    InventoryResponse refreshInventory(String userName);

    InventoryStatsResponse getStats() ;
}
