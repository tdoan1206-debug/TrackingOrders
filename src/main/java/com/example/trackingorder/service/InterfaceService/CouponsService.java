package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.response.coupons.CouponsResponse;

public interface CouponsService {
       CouponsResponse getCoupons(String couponCode) ;
}
