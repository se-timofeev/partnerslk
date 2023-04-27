package ru.planetnails.partnerslk.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vt_orderStatuses")
public class VtOrderStatuses {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime updated;
    @Column(name = "user_id")
    private String user;

    public VtOrderStatuses(OrderStatus orderStatus, LocalDateTime updated, String user) {
        this.orderStatus = orderStatus;
        this.updated = updated;
        this.user = user;
    }
}
