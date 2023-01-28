package ru.planetnails.partnerslk.model.order;

import lombok.*;
import ru.planetnails.partnerslk.model.user.User;


import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
@Builder
@Table(name = "vt_orderStatuses")
public class vtOrderStatuses {

    @ManyToOne(mappedBy = "orders", fetch = FetchType.LAZY)
    @Column(name = "orderId")
    private Long id;
    private LocalDateTime updated;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
