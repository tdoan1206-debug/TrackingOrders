package com.example.trackingorder.configMapper;

import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.tracking.TrackingLogs;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.status.WorkflowStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface TrackingLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", source = "order")
    @Mapping(target = "updater", source = "updater")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "loggedAt", source = "loggedAt")
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    TrackingLogs toTrackingLog(Orders order, Users updater, WorkflowStatus status, String note, LocalDateTime loggedAt);
}
