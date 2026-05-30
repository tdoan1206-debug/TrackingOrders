package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.request.fillter.OrderFillter;
import com.example.trackingorder.dto.request.order.BuyNowRequest;
import com.example.trackingorder.dto.request.order.OrderSummaryReq;
import com.example.trackingorder.dto.request.order.PlaceOrderReq;
import com.example.trackingorder.dto.response.order.*;

import java.util.List;


public interface OrdersService {
    OrdersSummaryResponse getOrdersSummary(OrderSummaryReq orderSummaryReq, String userName);

    PlaceOrderResponse placeOrder(PlaceOrderReq placeOrderReq, String userName);

    void byeNow(BuyNowRequest request, String userName);

    List<OrderResponse> getOrder(String userName);


    List<OrderResponse> getOrderFillter(OrderFillter orderFillter, String userName, Integer pageSize, Integer pageNumber);

    OrderStatusResponse cancelOrder(String id, String userName);

    OrderStatusResponse confirmOrder(String id, String userName);

    OrderStatisticsResponse getStatistics();

    List<MyOrderResponse> getMyOrder(String userName, Integer pageSize, Integer pageNumber);

    OrderDetailResponse getOrderDetail(String userName , String orderId) ;

    RevenueResponse getRevenue() ;

    void bulkConfirm() ;

    List<AllOrderResponse> getLatestOrders(Integer pageSize, Integer pageNumber) ;
}
