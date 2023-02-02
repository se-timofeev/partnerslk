package ru.planetnails.partnerslk.model.order;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.planetnails.partnerslk.model.contractor.Contractor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Order {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private UUID id;

    private Long num;
    private LocalDateTime orderDate;
    private Double sumWithoutDiscount;
    private Double sumOfDiscount;
    private Double sumWithDiscount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @Enumerated(EnumType.STRING)
    private OrderStatus Status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderVt> orderVts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<vtOrderStatuses> vtOrderStatuses;
}
