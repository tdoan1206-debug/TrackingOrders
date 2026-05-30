package com.example.trackingorder.dto.response.product;

import com.example.trackingorder.entity.products.Products;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private List<Response> responseList ;
}
