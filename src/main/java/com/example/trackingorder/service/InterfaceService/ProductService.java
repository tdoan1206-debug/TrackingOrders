package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.request.fillter.ProductFilter;
import com.example.trackingorder.dto.response.product.ProductDetail;
import com.example.trackingorder.dto.response.product.ProductFeaturedItem;
import com.example.trackingorder.dto.response.product.ProductFilterResponse;
import com.example.trackingorder.dto.response.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse search(String keyWord) ;
    List<ProductFeaturedItem> getFeaturedProducts(Integer pageSize , Integer pageNumber) ;
    ProductDetail   getProductDetail(String id);

    List<ProductFilterResponse> getProductFilter(ProductFilter productFilter , Integer pageSize , Integer pageNumber) ;
}
