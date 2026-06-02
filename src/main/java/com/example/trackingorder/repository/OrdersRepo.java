package com.example.trackingorder.repository;


import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.status.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrdersRepo extends JpaRepository<Orders, String>, JpaSpecificationExecutor<Orders> {

    @Query("""
                SELECT DISTINCT o
                FROM Orders o
                JOIN FETCH o.orderItems oi
                JOIN FETCH oi.productVariant pv
                JOIN FETCH pv.inventory
                WHERE o.user.userName = :userName
            """)
    List<Orders> findALlByUserName(String userName);

    @EntityGraph(attributePaths = {"paymentMethod", "orderItems"})
    Page<Orders> findAllByUser_UserName(String userName, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "deliveryAddress"})
    Page<Orders> findAll(Specification<Orders> spec, Pageable pageable);

    @Query("""
                SELECT DISTINCT o
                FROM Orders o
                 JOIN FETCH o.orderItems oi
                 JOIN FETCH oi.productVariant pv
                 JOIN FETCH pv.inventory
                WHERE o.id = :id
            """)
    Optional<Orders> findOrderForCancel(String id);

    @Query("""
                SELECT o.orderStatus, COUNT(o)
                FROM Orders o
                GROUP BY o.orderStatus
            """)
    List<Object[]> countOrdersByStatus();


    @EntityGraph(attributePaths = {"deliveryAddress", "paymentMethod", "shipments"})
    Optional<Orders> findByIdAndUser_UserName(String id, String userName);


    @Query("SELECT SUM(o.grandTotal) FROM Orders o WHERE o.orderStatus = 'SHIPPING'")
    BigDecimal getTotalRevenueShipping();


    List<Orders> findByOrderStatus(OrderStatus orderStatus);


    @EntityGraph(attributePaths = {"user"})
    Page<Orders> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
