package com.example.trackingorder.repository;

import com.example.trackingorder.entity.cartAndOrder.Coupons;
import com.example.trackingorder.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponsRepo  extends JpaRepository<Coupons,String>, JpaSpecificationExecutor<Coupons> {

    Optional<Coupons> getCouponsById(String couponId) ;

    @Query("Select c from Coupons c where c.Code = :code and c.status = :status ")
    Optional<Coupons> findByCodeAndStatus(String code, Status status) ;
}
