package com.example.trackingorder.dto.request.cartItems;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemsReq {

     @NotNull(message = "quantiry không được null ")
     @Min(value = 0, message = "Quantity phải lớn 0 ")
     private Long delta ;
}
