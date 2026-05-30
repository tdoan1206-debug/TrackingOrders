package com.example.trackingorder.dto.response.notify;

import com.example.trackingorder.status.NotifyStatus;
import com.example.trackingorder.status.NotifyType;
import com.example.trackingorder.status.WorkflowStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationResponse {
    private NotifyType type;
    private WorkflowStatus triggerStatus ;
    private NotifyStatus status ;
    private LocalDateTime scheduledAt;
    private String trackingNumber ;
}
