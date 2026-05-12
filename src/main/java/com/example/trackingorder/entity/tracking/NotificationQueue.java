package com.example.trackingorder.entity.tracking;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.status.NotifyStatus;
import com.example.trackingorder.status.NotifyType;
import com.example.trackingorder.status.WorkflowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_queue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationQueue extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    private NotifyType type;

    private WorkflowStatus triggerStatus ;

    private NotifyStatus status ;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;
}
