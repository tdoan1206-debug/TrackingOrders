package com.example.trackingorder.controller;

import com.example.trackingorder.dto.response.inventory.InventoryResponse;
import com.example.trackingorder.service.InterfaceService.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/inventory")
@RequiredArgsConstructor
@Validated
public class InventoryController {

    private final InventoryService inventoryService ;
    @PostMapping("/refresh")
    ResponseEntity<InventoryResponse> refreshInventory( Principal principal){
        InventoryResponse response =  inventoryService.refreshInventory( principal.getName()) ;

       return  ResponseEntity.ok(response) ;
    }
}
