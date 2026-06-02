package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.request.fillter.ProductFilter;
import com.example.trackingorder.dto.request.products.CreateProductRequest;
import com.example.trackingorder.dto.request.products.UpdateProductRequest;
import com.example.trackingorder.dto.response.product.*;

import java.util.List;

public interface ProductService {

    ProductResponse search(String keyWord) ;
    List<ProductFeaturedItem> getFeaturedProducts(Integer pageSize , Integer pageNumber) ;
    ProductDetail   getProductDetail(String id);

    List<ProductFilterResponse> getProductFilter(ProductFilter productFilter , Integer pageSize , Integer pageNumber) ;

    CreateProductResponse create(CreateProductRequest request , String userName) ;

    void update(UpdateProductRequest request , String variantId , String userName) ;
}
