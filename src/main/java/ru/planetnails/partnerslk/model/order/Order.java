package ru.planetnails.partnerslk.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.partner.Partner;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;
    private Long num;
    private LocalDateTime orderDate;
    private Double sumWithoutDiscount;
    private Double sumOfDiscount;
    private Double sumWithDiscount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderVt> orderVts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<VtOrderStatuses> vtOrderStatuses;

    public Order(Long num, LocalDateTime orderDate, Double sumWithoutDiscount, Double sumOfDiscount,
                 Double sumWithDiscount, Contractor contractor, Partner partner,
                 OrderStatus status, List<OrderVt> orderVts, List<VtOrderStatuses> vtOrderStatuses) {
        this.num = num;
        this.orderDate = orderDate;
        this.sumWithoutDiscount = sumWithoutDiscount;
        this.sumOfDiscount = sumOfDiscount;
        this.sumWithDiscount = sumWithDiscount;
        this.contractor = contractor;
        this.partner = partner;
        this.status = status;
        this.orderVts = orderVts;
        this.vtOrderStatuses = vtOrderStatuses;
    }
}
