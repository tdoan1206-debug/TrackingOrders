package com.example.trackingorder.repository;

import com.example.trackingorder.entity.cartAndOrder.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CartItemsRepo extends JpaRepository<CartItems,Long>, JpaSpecificationExecutor<CartItems> {
    List<CartItems> findByCartId(String cartId);
}
