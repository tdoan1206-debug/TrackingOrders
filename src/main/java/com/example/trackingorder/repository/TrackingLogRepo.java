package com.example.trackingorder.repository;

import com.example.trackingorder.entity.cartAndOrder.OrderItems;
import com.example.trackingorder.entity.tracking.TrackingLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TrackingLogRepo extends JpaRepository<TrackingLogs,String>, JpaSpecificationExecutor<TrackingLogs> {
}
