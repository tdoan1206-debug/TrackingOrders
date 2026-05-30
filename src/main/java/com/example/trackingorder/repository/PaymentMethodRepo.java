package com.example.trackingorder.repository;

import com.example.trackingorder.entity.payment.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethods,String>, JpaSpecificationExecutor<PaymentMethods> {

    Optional<PaymentMethods> findByIdAndIsActive(String id , Boolean isActive );
}
