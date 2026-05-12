package com.example.trackingorder.entity.tracking;

import com.example.trackingorder.entity.BaseEntity;
import com.example.trackingorder.entity.cartAndOrder.Orders;
import com.example.trackingorder.entity.payment.Shipments;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.status.WorkflowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackingLogs extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "status")
    private WorkflowStatus status;

    @Column(name = "location")
    private String location;

    @Column(name = "note")
    private String note;

    @Column(name = "logged_at")
    private LocalDateTime loggedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users updater ;
}
