package com.example.trackingorder.service.implementService;

import com.example.trackingorder.dto.response.inventory.CartItemsAndStatus;
import com.example.trackingorder.dto.response.inventory.InventoryResponse;
import com.example.trackingorder.dto.response.inventory.InventoryStatsResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.entity.cartAndOrder.Carts;
import com.example.trackingorder.entity.products.Inventory;
import com.example.trackingorder.exception.ResponseStatusException;
import com.example.trackingorder.repository.CartsRepo;
import com.example.trackingorder.repository.InventoryRepo;
import com.example.trackingorder.service.InterfaceService.InventoryService;
import com.example.trackingorder.status.InventoryStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class InventoryImplement implements InventoryService {
    private final InventoryRepo inventoryRepo;
    private final CartsRepo cartsRepo;

    @Override
    public InventoryStatsResponse getStats() {
        List<Inventory> inventories = inventoryRepo.findAll();
        BigDecimal totalInventoryValue = BigDecimal.ZERO;
        long lowStockCount = 0;
        long outOfStockCount = 0;
        long inStockCount = 0;

        for (Inventory inventory : inventories) {

            Long quantity = inventory.getQuantityInStock();
            
            BigDecimal price = inventory.getProductVariant().getPrice();
            
            totalInventoryValue = totalInventoryValue.add(price.multiply(BigDecimal.valueOf(quantity)));

            // Thống kê trạng thái
            InventoryStatus status = inventory.getStatus();

            if (status == InventoryStatus.IN_STOCK) {
                inStockCount++;
            } else if (status == InventoryStatus.LIMIDTED_STOCK) {
                lowStockCount++;
            } else if (status == InventoryStatus.OUT_OF_STOCK) {
                outOfStockCount++;
            }
        }

        InventoryStatsResponse response = new InventoryStatsResponse();

        response.setTotalInventoryValue(totalInventoryValue);
        response.setTotalProducts(inventories.size());
        response.setInStockCount(inStockCount);
        response.setLowStockCount(lowStockCount);
        response.setOutOfStockCount(outOfStockCount);

        return response;

    }

    @Override
    @Transactional
    public InventoryResponse refreshInventory(String userName) {
        Carts carts = cartsRepo.findCartsByUserNameAndStatus(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong thay cart cua ban"));

        List<CartItemsAndStatus> inventoryResponse = new ArrayList<>();
        for (CartItems items : carts.getCartItemsList()) {

            Inventory inventory = items.getProductVariant().getInventory() ;
            Long stockQuantity = inventory.getQuantityInStock();

            InventoryStatus status  ;
            if (stockQuantity <= 0) {
                status = InventoryStatus.OUT_OF_STOCK;
            }
            else if (stockQuantity < 5) {
                status = InventoryStatus.LIMIDTED_STOCK;
            }
            else {
                status = InventoryStatus.IN_STOCK;
            }

            inventory.setStatus(status);
            CartItemsAndStatus cartItemsAndStatus = new CartItemsAndStatus() ;
            cartItemsAndStatus.setCartItemsName(items.getProductVariant().getProduct().getName());
            cartItemsAndStatus.setStatus(status);

            inventoryResponse.add(cartItemsAndStatus) ;
        }
        InventoryResponse inventoryResponse1 = new InventoryResponse() ;
        inventoryResponse1.setCartItemsAndStatusList(inventoryResponse);
        return inventoryResponse1 ;
    }
}
