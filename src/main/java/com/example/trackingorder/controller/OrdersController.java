package com.example.trackingorder.controller;

import com.example.trackingorder.dto.request.fillter.OrderFillter;
import com.example.trackingorder.dto.request.order.BuyNowRequest;
import com.example.trackingorder.dto.request.order.OrderSummaryReq;
import com.example.trackingorder.dto.request.order.PlaceOrderReq;
import com.example.trackingorder.dto.response.order.*;
import com.example.trackingorder.service.InterfaceService.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrdersController {

    private final OrdersService ordersService ;

    @PostMapping("/summary")
    ResponseEntity<OrdersSummaryResponse> getSummary(@RequestBody OrderSummaryReq orderSummaryReq, Principal principal){
        OrdersSummaryResponse response = ordersService.getOrdersSummary(orderSummaryReq,principal.getName());
        return ResponseEntity.ok(response) ;
    }

    @PostMapping("/place-order")
    ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderReq placeOrderReq, Principal principal){
        PlaceOrderResponse response = ordersService.placeOrder(placeOrderReq, principal.getName());
        return ResponseEntity.ok(response) ;
    }

    @PostMapping("/bye-now")
    ResponseEntity<String> placeOrder(@Valid @RequestBody BuyNowRequest request, Principal principal){
        ordersService.byeNow(request, principal.getName());
        return ResponseEntity.ok("Đã Đặt hàng") ;
    }


    @GetMapping()
    ResponseEntity<List<OrderResponse>> getOrder(Principal principal){
        List<OrderResponse> responseList = ordersService.getOrder(principal.getName());
        return ResponseEntity.ok(responseList) ;
    }


    @GetMapping("/filter")
    public ResponseEntity<List<OrderResponse>> getOrderFillter(OrderFillter orderFillter,Principal principal ,
                                                               @RequestParam(name = "page_size") Integer pageSize ,
                                                               @RequestParam(name = "page_number") Integer pageNumber){
        List<OrderResponse> responseList = ordersService.getOrderFillter(orderFillter,principal.getName() ,pageSize,pageNumber) ;
        return ResponseEntity.ok(responseList) ;
    }

    @PutMapping("/orders/{id}/CANCELLED")
    public ResponseEntity<OrderStatusResponse> cancelOrder(@PathVariable String id,Principal principal ) {
        OrderStatusResponse result = ordersService.cancelOrder(id,principal.getName());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/orders/{id}/confirm")
    public ResponseEntity<OrderStatusResponse> confirmOrder(@PathVariable String id,Principal principal ) {
        OrderStatusResponse result = ordersService.confirmOrder(id,principal.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAnyRole('Seller')")
    public ResponseEntity<OrderStatisticsResponse> getStatistics() {
        OrderStatisticsResponse result = ordersService.getStatistics();
        return ResponseEntity.ok(result);
    }

    @GetMapping("my-order")
    ResponseEntity<List<MyOrderResponse>> getMyOrder(Principal principal,
                                                   @RequestParam(name = "page_size") Integer pageSize ,
                                                   @RequestParam(name = "page_number") Integer pageNumber){
        List<MyOrderResponse> responseList = ordersService.getMyOrder(principal.getName(),pageSize,pageNumber);
        return ResponseEntity.ok(responseList) ;
    }

//    @GetMapping("/{orderId}/tracking")
//    public ResponseEntity<OrderTrackingResponse> getTracking(Principal principal ,@PathVariable String orderId) {
//
//        OrderTrackingResponse response = ordersService.getTracking(principal.getName(), orderId);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(Principal principal ,@PathVariable String orderId) {

        OrderDetailResponse response = ordersService.getOrderDetail(principal.getName(), orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> getRevenue() {
        return ResponseEntity.ok(ordersService.getRevenue());
    }


    @PutMapping("/bulk-confirm")
    public ResponseEntity<String> bulkConfirm() {
        ordersService.bulkConfirm();
        return ResponseEntity.ok("Updated  orders");
    }


    @GetMapping("all-orders")
    ResponseEntity<List<AllOrderResponse>> getLatestOrders(@RequestParam(name = "page_size") Integer pageSize ,
                                                          @RequestParam(name = "page_number") Integer pageNumber){
        List<AllOrderResponse> responseList = ordersService.getLatestOrders(pageSize,pageNumber);
        return ResponseEntity.ok(responseList) ;
    }
}
