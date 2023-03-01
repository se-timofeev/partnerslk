package ru.planetnails.partnerslk.model.order;

import lombok.*;
import ru.planetnails.partnerslk.model.user.User;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vt_orderStatuses")
public class vtOrderStatuses {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime updated;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public vtOrderStatuses(OrderStatus orderStatus, LocalDateTime updated, User user) {
        this.orderStatus = orderStatus;
        this.updated = updated;
        this.user = user;
    }
}
