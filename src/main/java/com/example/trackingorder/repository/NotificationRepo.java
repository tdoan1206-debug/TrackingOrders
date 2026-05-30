package com.example.trackingorder.repository;

import com.example.trackingorder.entity.tracking.NotificationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepo extends JpaRepository<NotificationQueue, String>, JpaSpecificationExecutor<NotificationQueue> {
    @Query("SELECT nq FROM NotificationQueue nq JOIN fetch nq.user u WHERE u.userName = :userName")
    List<NotificationQueue> findByUserName(String userName);
}
