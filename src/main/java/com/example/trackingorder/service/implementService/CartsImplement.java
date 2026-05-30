package com.example.trackingorder.service.implementService;

import com.example.trackingorder.configMapper.CartsMapper;
import com.example.trackingorder.dto.request.cart.AddToCartRequest;
import com.example.trackingorder.dto.response.cartItems.CartItemsAndCartResponse;
import com.example.trackingorder.dto.response.carts.CartsResponse;
import com.example.trackingorder.entity.cartAndOrder.CartItems;
import com.example.trackingorder.entity.cartAndOrder.Carts;
import com.example.trackingorder.entity.products.Inventory;
import com.example.trackingorder.entity.products.ProductVariant;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.exception.ResponseStatusException;
import com.example.trackingorder.repository.*;
import com.example.trackingorder.service.InterfaceService.CartsService;
import com.example.trackingorder.status.CartStatus;
import com.example.trackingorder.status.InventoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartsImplement implements CartsService {

    private final CartsRepo cartsRepo;
    private final CartsMapper cartsMapper;
    private final CartItemsRepo cartItemsRepo;
    private final ProductVariantRepo productVariantRepo;
    private final InventoryRepo inventoryRepo;
    private final UserRepo userRepo;

    @Override
    public void addToCart(String userName, AddToCartRequest request) {

        ProductVariant productVariant = productVariantRepo.findById(request.getProductVariantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại"));

        Inventory inventory = inventoryRepo.findByProductVariantId(request.getProductVariantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tồn kho không có thông tin "));

        Long available = inventory.getQuantityInStock() - inventory.getReservedQuantity();
        if (available < request.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sản phẩm không đủ số lượng trong kho. Còn lại: " + available);
        }
        Carts cart = cartsRepo.findCartsByUserNameAndStatus(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng chưa có Cart cá nhân "));


        CartItems cartItem = cartItemsRepo.findByCartIdAndProductVariantId(cart.getId(), productVariant.getId());

        if (cartItem != null) {
            Long newQuantity = cartItem.getQuantity() + request.getQuantity();

            if( newQuantity > available ){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ko đủ tồn kho") ;
            }

            cartItem.setQuantity(newQuantity);
            cartItemsRepo.save(cartItem);
        }
        else{
            CartItems newCartItem = new CartItems();
            newCartItem.setCart(cart);
            newCartItem.setProductVariant(productVariant);
            newCartItem.setQuantity(request.getQuantity());
            cartItemsRepo.save(newCartItem);

        }

        List<CartItems> cartItems = cartItemsRepo.findAllByCartId(cart.getId());

        BigDecimal subTotal = BigDecimal.ZERO;

        for (CartItems item : cartItems) {

            BigDecimal itemTotal = item.getProductVariant().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            subTotal = subTotal.add(itemTotal);
        }
        cart.setSubTotal(subTotal);
        cartsRepo.save(cart);
    }

    @Override
    public CartsResponse getMyCart(String userName) {
        Optional<Carts> optionalCarts = cartsRepo.findCartsByUserNameAndStatus(userName);
        if (optionalCarts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy cart của bạn");
        }
        Carts carts = optionalCarts.get();

        CartsResponse cartsResponse = cartsMapper.toResponse(carts);


        Boolean checkOutStock = false;
        for (CartItemsAndCartResponse response : cartsResponse.getItems()) {
            String stockStatus = response.getStockStatus();
            if (stockStatus.equals("OUT_OF_STOCK")) {
                checkOutStock = true;
                break;
            }
        }
        if (checkOutStock) {
            cartsResponse.setValidationMessage("MỘt số sản phẩm đã hết hàng , vui longf xóa khỏi giỏ hàng");
        }
        return cartsResponse;

    }
}
