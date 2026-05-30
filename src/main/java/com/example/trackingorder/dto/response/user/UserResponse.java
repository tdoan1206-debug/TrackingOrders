package com.example.trackingorder.dto.response.user;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String email ;

    private String fullName ;

    private String phone ;
}
