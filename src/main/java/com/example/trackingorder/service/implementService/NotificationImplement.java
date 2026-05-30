package com.example.trackingorder.service.implementService;

import com.example.trackingorder.configMapper.NotifyMapper;
import com.example.trackingorder.dto.response.notify.NotificationResponse;
import com.example.trackingorder.entity.tracking.NotificationQueue;
import com.example.trackingorder.repository.NotificationRepo;
import com.example.trackingorder.service.InterfaceService.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationImplement implements NotificationService {
    private final NotificationRepo notificationRepo ;
    private final NotifyMapper notifyMapper ;

    @Override
    public List<NotificationResponse> getNotify(String userName) {
          List<NotificationQueue> notificationQueueList = notificationRepo.findByUserName(userName) ;


          List<NotificationResponse> notificationResponseList = new ArrayList<>() ;
          for(NotificationQueue notificationQueue : notificationQueueList){
              NotificationResponse response = notifyMapper.toResponse(notificationQueue);

              notificationResponseList.add(response);
          }
          return notificationResponseList ;
    }
}
