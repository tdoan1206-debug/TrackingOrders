package com.example.trackingorder.service.InterfaceService;

import com.example.trackingorder.dto.response.notify.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getNotify(String userName) ;
}
