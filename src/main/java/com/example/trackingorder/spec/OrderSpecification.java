package com.example.trackingorder.spec;

import com.example.trackingorder.dto.response.order.OrderResponse;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.status.OrderStatus;
import com.example.trackingorder.status.PaymentStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSpecification {

    public static Specification<Orders> equalUserName(String userName) {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.like(root.get("user").get("userName") , userName ) ;
            }
        };
    }

    public static Specification<Orders> likeOrderId(String orderId) {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if(orderId == null || orderId.isEmpty()){
                    return null ;
                }
                return criteriaBuilder.like(root.get("id") , "%" + orderId + "%") ;
            }
        };
    }
    public static Specification<Orders> equalOrderStatus(OrderStatus status) {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if (status == null) return null;
                return criteriaBuilder.equal(root.get("orderStatus") , status) ;
            }
        };
    }

    public static Specification<Orders> equalPaymentStatus(PaymentStatus status) {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if (status == null) return null;
                return criteriaBuilder.equal(root.get("paymentStatus") , status) ;
            }
        };
    }

    public static Specification<Orders> minTotal(BigDecimal minTotal) {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (minTotal == null) return null;
                return criteriaBuilder.greaterThanOrEqualTo(root.get("grandTotal") , minTotal ) ;
            }
        };
    }

    public static Specification<Orders> maxTotal(BigDecimal maxTotal)  {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (maxTotal == null) return null;
                return criteriaBuilder.lessThanOrEqualTo(root.get("grandTotal") , maxTotal ) ;
            }
        };
    }


    public static Specification<Orders> fromDate(LocalDateTime fromDate) {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (fromDate == null) return null;
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt") , fromDate) ;
            }
        };
    }
    public static Specification<Orders> toDate(LocalDateTime toDate)  {
        return new Specification<Orders>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (toDate == null) return null;
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt") , toDate) ;
            }
        };
    }

}
