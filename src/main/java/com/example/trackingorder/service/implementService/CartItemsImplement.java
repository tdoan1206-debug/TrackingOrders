package com.example.trackingorder.service.implementService;

import com.example.trackingorder.repository.CartItemsRepo;
import com.example.trackingorder.service.InterfaceService.CartItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemsImplement implements CartItemsService {
     private final CartItemsRepo cartItemsRepo ;
}
