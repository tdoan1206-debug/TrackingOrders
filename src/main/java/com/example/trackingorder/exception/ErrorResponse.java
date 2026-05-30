package com.example.trackingorder.exception;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @NotNull
    private String message;

    @NotNull
    private Integer code;

    Map<String, String> errors = new HashMap<>();
}
