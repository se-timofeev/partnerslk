package ru.planetnails.partnerslk.model.order;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.planetnails.partnerslk.model.contractor.Contractor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
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

    public Order(Long num, LocalDateTime orderDate, Double sumWithoutDiscount, Double sumOfDiscount,
                 Double sumWithDiscount, Contractor contractor, OrderStatus status, List<OrderVt> orderVts,
                 List<vtOrderStatuses> vtOrderStatuses) {
        this.num = num;
        this.orderDate = orderDate;
        this.sumWithoutDiscount = sumWithoutDiscount;
        this.sumOfDiscount = sumOfDiscount;
        this.sumWithDiscount = sumWithDiscount;
        this.contractor = contractor;
        Status = status;
        this.orderVts = orderVts;
        this.vtOrderStatuses = vtOrderStatuses;
    }
}
