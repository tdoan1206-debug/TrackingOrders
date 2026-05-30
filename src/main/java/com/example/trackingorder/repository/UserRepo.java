package com.example.trackingorder.repository;

import com.example.trackingorder.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,String>, JpaSpecificationExecutor<Users> {
    Optional<Users> findByUserName(String userName);
}
