package com.example.trackingorder.configMapper;

import com.example.trackingorder.dto.response.notify.NotificationResponse;
import com.example.trackingorder.entity.payment.Shipments;
import com.example.trackingorder.entity.tracking.NotificationQueue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotifyMapper {

    @Mapping(source = "order.shipments", target = "trackingNumber", qualifiedByName = "mapTrackingNumber")
    NotificationResponse toResponse(NotificationQueue notificationQueue);

    @Named("mapTrackingNumber")
    default String mapTrackingNumber(List<Shipments> shipments) {
        if (shipments == null || shipments.isEmpty()) {
            return null;
        }
        return shipments.getFirst().getTrackingNumber();
    }
}
