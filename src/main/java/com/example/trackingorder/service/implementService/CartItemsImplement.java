package com.example.trackingorder.service.implementService;

import com.example.trackingorder.configMapper.CartItemsMapper;
import com.example.trackingorder.dto.request.cartItems.CartItemsReq;
import com.example.trackingorder.dto.response.cartItems.CartItemsResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.entity.cartAndOrder.Carts;
import com.example.trackingorder.entity.products.Inventory;
import com.example.trackingorder.repository.CartItemsRepo;
import com.example.trackingorder.repository.CartsRepo;
import com.example.trackingorder.repository.InventoryRepo;
import com.example.trackingorder.service.InterfaceService.CartItemsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class CartItemsImplement implements CartItemsService {
    private final CartItemsRepo cartItemsRepo;
    private final InventoryRepo inventoryRepo;
    private final CartsRepo cartsRepo;
    private final CartItemsMapper cartItemsMapper;

    @Override
    public CartItems findById(String cartItemsId) {
        Optional<CartItems> cartIems = cartItemsRepo.findById(cartItemsId);
        if (cartIems.isEmpty()) {
            throw new RuntimeException("Khong tim thay cartItems");
        } else {
            return cartIems.get();
        }
    }

    @Override
    @Transactional
    public CartItemsResponse updateQuantity(String cartItemsId, CartItemsReq cartItemsReq) {
        CartItems cartItems = findById(cartItemsId);

        Optional<Inventory> optionalInventory = inventoryRepo.findByProductVariantId(cartItems.getProductVariant().getId());
        if (!optionalInventory.isPresent()) {
            throw new RuntimeException("Inventory không thấy");
        }

        Inventory inventory = optionalInventory.get();


        Long oleQuantity = cartItems.getQuantity();
        Long newQuantity = oleQuantity + cartItemsReq.getDelta();
        Long quantityInStock = inventory.getQuantityInStock();
        if (newQuantity < 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }
        if (quantityInStock < 0 && newQuantity > quantityInStock) {
            throw new RuntimeException("Hết hàng");
        }

        cartItems.setQuantity(newQuantity);
        cartItemsRepo.save(cartItems);


        BigDecimal variantPrice = cartItems.getProductVariant().getPrice();
        BigDecimal oldItemsSubTotal = variantPrice.multiply(BigDecimal.valueOf(oleQuantity));
        BigDecimal newItemsSubTotal = variantPrice.multiply(BigDecimal.valueOf(newQuantity));

        Carts carts = cartItems.getCart();
        BigDecimal cartSubtotal = carts.getSubTotal();
        BigDecimal deltaSubTotal = newItemsSubTotal.subtract(oldItemsSubTotal);
        BigDecimal newCartSubTotal = cartSubtotal.add(deltaSubTotal);

        carts.setSubTotal(newCartSubTotal);
        cartsRepo.save(carts);

        CartItemsResponse respon = cartItemsMapper.toResponse(cartItems, newItemsSubTotal, newCartSubTotal);
        return respon;
    }
}
