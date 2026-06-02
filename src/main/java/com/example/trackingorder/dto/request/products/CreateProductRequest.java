package com.example.trackingorder.dto.request.products;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductRequest {
    @NotBlank(message = "Tên sản phẩm không được trống")
    @Size(max = 500)
    private String productName;

    @NotBlank(message = "Danh mục không được trống")
    private String categoryId;

    @NotEmpty(message = "Phải có ít nhất 1 variant")
    @Valid
    private List<VariantRequest> variants;
}
