package com.example.trackingorder.service.implementService;

import com.example.trackingorder.dto.response.coupons.CouponsResponse;
import com.example.trackingorder.entity.cartAndOrder.Coupons;
import com.example.trackingorder.exception.ResponseStatusException;
import com.example.trackingorder.repository.CouponsRepo;
import com.example.trackingorder.service.InterfaceService.CouponsService;
import com.example.trackingorder.status.Status;
import com.example.trackingorder.status.Type;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponsImplement implements CouponsService {
    private final CouponsRepo couponsRepo;

    @Override
    @Transactional
    public CouponsResponse getCoupons(String couponCode) {
        Coupons coupons = couponsRepo.findByCodeAndStatus(couponCode, Status.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon không tồn tại"));

        if (coupons.getStatus() != Status.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon không còn hiệu lực");
        }
        if (coupons.getUseCount() >= coupons.getUseLimit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon đã hết lượt sử dụng");
        }
        coupons.setUseCount(coupons.getUseCount() + 1);
        couponsRepo.save(coupons);

        CouponsResponse couponsResponse = new CouponsResponse();

        couponsResponse.setId(coupons.getId());
        couponsResponse.setCouponType(coupons.getType());
        couponsResponse.setValue(coupons.getValue());
        return couponsResponse;
    }
}
