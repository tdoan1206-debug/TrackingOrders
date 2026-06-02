package com.example.trackingorder.service.implementService;

import com.example.trackingorder.configMapper.ProductDetailMapper;
import com.example.trackingorder.configMapper.ProductFeaturedMapper;
import com.example.trackingorder.dto.request.fillter.ProductFilter;
import com.example.trackingorder.dto.request.products.CreateProductRequest;
import com.example.trackingorder.dto.request.products.UpdateProductRequest;
import com.example.trackingorder.dto.request.products.VariantRequest;
import com.example.trackingorder.dto.response.product.*;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.products.Categories;
import com.example.trackingorder.entity.products.Inventory;
import com.example.trackingorder.entity.products.ProductVariant;
import com.example.trackingorder.entity.products.Products;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.exception.ResponseStatusException;
import com.example.trackingorder.repository.*;
import com.example.trackingorder.service.InterfaceService.ProductService;
import com.example.trackingorder.spec.OrderSpecification;
import com.example.trackingorder.spec.ProductSpecification;
import com.example.trackingorder.status.InventoryStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductImplement implements ProductService {

    private final ProductRepo productRepo;
    private final ProductVariantRepo productVariantRepo ;
    private final ProductFeaturedMapper productFeaturedMapper;
    private final ProductDetailMapper productDetailMapper ;
    private final CategoryRepo categoryRepo ;
    private final UserRepo userRepo ;
    private final InventoryRepo inventoryRepo;

    @Override
    @Transactional
    public void update(UpdateProductRequest request, String variantId, String userName) {

        ProductVariant variant = productVariantRepo.findById(variantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sản phẩm không tồn tại"));

        String existingSkus = productVariantRepo.findExistingSku(request.getSku());
        if (existingSkus != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Có SKU đã tồn tại là " );
        }

        variant.setSku(request.getSku());
        variant.setPrice(request.getPrice());
        variant.setColor(request.getColor());
        variant.setSize(request.getSize());
        variant.setDescription(request.getDescription());
        variant.setWeightGram((long) request.getWeightGram());

        Inventory inventory = variant.getInventory();

        if (inventory != null) {

            inventory.setQuantityInStock(request.getQuantityInStock());

            Long quantity = request.getQuantityInStock();

            if (quantity == 0) {
                inventory.setStatus(InventoryStatus.OUT_OF_STOCK);
            } else if (quantity <= 5) {
                inventory.setStatus(InventoryStatus.LIMIDTED_STOCK);
            } else {
                inventory.setStatus(InventoryStatus.IN_STOCK);
            }

            inventoryRepo.save(inventory);
        }
        productVariantRepo.save(variant);
    }

    @Override
    @Transactional
    public CreateProductResponse create(CreateProductRequest request, String userName) {
        Categories cat = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Danh mục không tồn tại"));
        Users user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "người dùng không tồn tại"));

        List<String> skus = new ArrayList<>();
        for (VariantRequest variant : request.getVariants()) {
            skus.add(variant.getSku());
        }

        List<String> existingSkus = productVariantRepo.findExistingSkus(skus);
        if (!existingSkus.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Có SKU đã tồn tại là " +existingSkus.get(0));
        }

        Products product = new Products();
        product.setName(request.getProductName());
        product.setCategory(cat);
        product.setUsers(user);

        product = productRepo.save(product);

        List<String> variantIds = new ArrayList<>();
        for (VariantRequest variantRequest : request.getVariants()) {

            ProductVariant variant = new ProductVariant();

            variant.setSku(variantRequest.getSku());
            variant.setPrice(variantRequest.getPrice());
            variant.setOriginalPrice(variantRequest.getOriginalPrice());
            variant.setColor(variantRequest.getColor());
            variant.setSize(variantRequest.getSize());
            variant.setDescription(variantRequest.getDescription());
            variant.setWeightGram((long) variantRequest.getWeightGram());

            variant.setProduct(product);

            productVariantRepo.save(variant);

            variantIds.add(variant.getId());
        }


        CreateProductResponse response = new CreateProductResponse();
        response.setProductId(product.getId());
        response.setVariantIds(variantIds);
        response.setMessage("Tạo sản phẩm thành công");

        return response;

    }

    @Override
    public List<ProductFilterResponse> getProductFilter(ProductFilter productFilter, Integer pageSize, Integer pageNumber) {
        Specification<Products> specification = (root, query, cb) -> cb.conjunction();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        if (productFilter.getKeyword() != null && !productFilter.getKeyword().isEmpty()) {
            specification = specification.and(ProductSpecification.likeKeyWord(productFilter.getKeyword()));
        }
        if (productFilter.getCategoryId() != null && !productFilter.getCategoryId() .isEmpty()) {
            specification = specification.and(ProductSpecification.equalCategoryId(productFilter.getCategoryId()));
        }
        if (productFilter.getStatus() != null) {
            specification = specification.and(ProductSpecification.equalInventoryStatus(productFilter.getStatus()));
        }
        if (productFilter.getMinPrice() != null ) {
            specification = specification.and(ProductSpecification.minPrice(productFilter.getMinPrice()));
        }

        if (productFilter.getMaxPrice() != null ) {
            specification = specification.and(ProductSpecification.maxPrice(productFilter.getMaxPrice()));
        }

        Page<Products> productsPage = productRepo.findAll(specification,pageable) ;

        List<ProductFilterResponse> responseList = new ArrayList<>() ;

        for( Products product : productsPage.getContent()){

            if (product.getProductVariants() == null || product.getProductVariants().isEmpty()) {
                continue;
            }
            for (ProductVariant variant : product.getProductVariants()) {
                ProductFilterResponse response = new ProductFilterResponse();
                response.setProductName(product.getName());
                response.setProductVariantId(variant.getId());
                response.setSku(variant.getSku());
                response.setPrice(variant.getPrice());
                response.setCategoryName(product.getName());
                response.setQuantityInStock(variant.getInventory().getQuantityInStock());
                response.setStatus(variant.getInventory().getStatus());

                responseList.add(response);
            }

        }
        return responseList ;
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
