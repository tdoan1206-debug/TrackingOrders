package com.example.trackingorder.dto.response.order;

import com.example.trackingorder.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class OrderStatisticsResponse {


    private Long totalOrders;

    private Map<OrderStatus, Long> orderStatusStatistics;
}