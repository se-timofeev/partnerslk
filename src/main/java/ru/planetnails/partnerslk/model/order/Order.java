package ru.planetnails.partnerslk.model.order;

import lombok.*;
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
public class Order{

    @Id
    @GeneratedValue
    private UUID id_guid;
    private Long num;
    private LocalDateTime orderDate;
    private Double sumWithoutDiscount;
    private Double sumOfDiscount;
    private Double sumWithDiscount;
    @OneToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @JoinColumn(name = "n_row")
    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderVt> orderVts;
    @JoinColumn(name = "status_id")
    @OneToMany
    private List<vtOrderStatuses> vtOrderStatuses;
}
