package com.example.trackingorder.repository;

import com.example.trackingorder.entity.cartAndOrder.OrderItems;
import com.example.trackingorder.entity.users.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemsRepo extends JpaRepository<OrderItems,String>, JpaSpecificationExecutor<OrderItems> {

    @Query("""
        SELECT oi FROM OrderItems oi
        JOIN FETCH oi.productVariant pv
        JOIN FETCH pv.product
        WHERE oi.order.id = :orderId
    """)
    List<OrderItems> findByOrder_Id(String orderId);
}
