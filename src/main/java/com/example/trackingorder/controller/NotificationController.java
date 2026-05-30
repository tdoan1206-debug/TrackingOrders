package com.example.trackingorder.controller;

import com.example.trackingorder.dto.response.notify.NotificationResponse;
import com.example.trackingorder.service.InterfaceService.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
@Validated
public class NotificationController {

    private final NotificationService notificationService ;

    @GetMapping()
    ResponseEntity<List<NotificationResponse>> getNotify(Principal principal){

        List<NotificationResponse> notificationResponse = notificationService.getNotify(principal.getName()) ;

        return ResponseEntity.ok(notificationResponse) ;
    }
}
