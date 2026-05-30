package com.example.trackingorder.repository;

import com.example.trackingorder.entity.cartAndOrder.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartsRepo extends JpaRepository<Carts,String>, JpaSpecificationExecutor<Carts> {

    @Query("""
        SELECT DISTINCT c 
        FROM Carts c
        JOIN fetch c.cartItemsList ci
        JOIN fetch ci.productVariant pv
        JOIN fetch pv.inventory
        JOIN FETCH pv.product
        WHERE c.user.userName = :userName
          AND c.status = 'ACTIVE'
    """)
    Optional<Carts> findCartsByUserNameAndStatus(String userName);

}
