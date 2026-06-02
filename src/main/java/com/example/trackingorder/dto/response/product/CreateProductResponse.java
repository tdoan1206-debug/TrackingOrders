package com.example.trackingorder.dto.response.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductResponse {
    private String productId;
    private List<String> variantIds;
    private String message;
}
