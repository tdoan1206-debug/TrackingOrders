package com.example.trackingorder.repository;

import com.example.trackingorder.entity.users.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DeliveryAddressRepo extends JpaRepository<DeliveryAddress,String>, JpaSpecificationExecutor<DeliveryAddress> {
    Optional<DeliveryAddress> findByIdAndUserId(String id , String userId);
}
