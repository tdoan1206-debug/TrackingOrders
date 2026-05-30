package com.example.trackingorder.repository;

import com.example.trackingorder.entity.cartAndOrder.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemsRepo extends JpaRepository<CartItems, String>, JpaSpecificationExecutor<CartItems> {
    List<CartItems> findByCartId(String cartId);

    CartItems findBy(String cartItemsId);

    @Query("select ci from CartItems ci where ci.cart.user.userName = :userName")
    List<CartItems> findCartItemsByUserName(String userName);

    @Query("select c from CartItems c join fetch c.productVariant  where c.id in :ids and c.cart.user.userName = :userName")
    List<CartItems> findByIdInAndUserName(List<String> ids, String userName);


    @Query("""
                SELECT ci
                FROM CartItems ci
                JOIN FETCH ci.productVariant
                WHERE ci.cart.id = :cartId
            """)
    List<CartItems> findAllByCartId(String cartId);

    CartItems findByCartIdAndProductVariantId(String cartId, String productVariantId);
}
