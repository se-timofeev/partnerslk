package ru.planetnails.partnerslk.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderStatus;
import ru.planetnails.partnerslk.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Notification {

    @Id
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Order order;
    @Column(name = "is_read")
    private boolean isRead;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
