package com.example.trackingorder.controller;

import com.example.trackingorder.dto.request.fillter.OrderFillter;
import com.example.trackingorder.dto.request.fillter.ProductFilter;
import com.example.trackingorder.dto.request.products.CreateProductRequest;
import com.example.trackingorder.dto.request.products.UpdateProductRequest;
import com.example.trackingorder.dto.response.order.OrderResponse;
import com.example.trackingorder.dto.response.product.*;
import com.example.trackingorder.service.InterfaceService.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService ;
    @GetMapping("/search")
    ResponseEntity<ProductResponse> searchProduct (@RequestParam String keyWord){
        ProductResponse response = productService.search( keyWord ) ;
        return  ResponseEntity.ok(response) ;
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ProductFeaturedItem>> getFeaturedProducts(@RequestParam(name = "page_size") Integer pageSize ,
                                                                         @RequestParam(name = "page_number") Integer pageNumber) {
        return ResponseEntity.ok(productService.getFeaturedProducts(pageSize,pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetail> getProductDetail(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductDetail(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductFilterResponse>> getProductFilter(ProductFilter productFilter ,
                                                                         @RequestParam(name = "page_size") Integer pageSize ,
                                                                         @RequestParam(name = "page_number") Integer pageNumber){
        List<ProductFilterResponse> responseList = productService.getProductFilter(productFilter,pageSize,pageNumber) ;
        return ResponseEntity.ok(responseList) ;
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<CreateProductResponse> create(@Valid @RequestBody CreateProductRequest request, Principal principal) {

        CreateProductResponse response = productService.create(request , principal.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{variantId}")
//    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<String> update(@Valid @RequestBody UpdateProductRequest request,
                                                        @PathVariable String variantId, Principal principal) {

        productService.update(request ,variantId, principal.getName());
        return ResponseEntity.ok("Cập Nhật thành công");
    }

}
