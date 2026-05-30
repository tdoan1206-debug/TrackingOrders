package com.example.trackingorder.service.implementService;

import com.example.trackingorder.configMapper.ProductDetailMapper;
import com.example.trackingorder.configMapper.ProductFeaturedMapper;
import com.example.trackingorder.dto.request.fillter.ProductFilter;
import com.example.trackingorder.dto.response.product.*;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.products.ProductVariant;
import com.example.trackingorder.entity.products.Products;
import com.example.trackingorder.exception.ResponseStatusException;
import com.example.trackingorder.repository.ProductRepo;
import com.example.trackingorder.repository.ProductVariantRepo;
import com.example.trackingorder.service.InterfaceService.ProductService;
import com.example.trackingorder.spec.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImplement implements ProductService {

    private final ProductRepo productRepo;
    private final ProductVariantRepo productVariantRepo ;
    private final ProductFeaturedMapper productFeaturedMapper;
    private final ProductDetailMapper productDetailMapper ;

    @Override
    public List<ProductFilterResponse> getProductFilter(ProductFilter productFilter, Integer pageSize, Integer pageNumber) {
        Specification<Products> specification = (root, query, cb) -> cb.conjunction();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);


    }

    @Override
    public List<ProductFeaturedItem> getFeaturedProducts(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Products> products = productRepo.findAll(pageable);

        List<ProductFeaturedItem> productFeaturedItems = new ArrayList<>();
        for (Products item : products) {
            if (item.getProductVariants().isEmpty()) {
                continue;
            }
            ProductFeaturedItem productFeaturedItem =  productFeaturedMapper.toProductFeaturedItem(item);
            productFeaturedItems.add(productFeaturedItem);
        }
        return productFeaturedItems;
    }

    @Override
    public ProductDetail getProductDetail(String id) {
        ProductVariant variant = productVariantRepo.findDetailByProductId(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm"));
        ProductDetail response = productDetailMapper.toProductDetail(variant);

        return response;
    }


    @Override
    public ProductResponse search(String keyWord) {
        List<Products> productsList = productRepo.findByNameContainingIgnoreCase(keyWord);

        List<Response> responses = new ArrayList<>();
        for (Products product : productsList) {
            Response response = new Response();
            response.setProductName(product.getName());
            responses.add(response);
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setResponseList(responses);

        return productResponse;
    }


}
