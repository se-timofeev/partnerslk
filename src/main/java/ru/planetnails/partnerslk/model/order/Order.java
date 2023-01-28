package ru.planetnails.partnerslk.model.order;

import lombok.*;
import ru.planetnails.partnerslk.model.contractor.Contractor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @Column(name = "id_guid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @JoinColumn
    @OneToMany(mappedBy = "orders_vt", fetch = FetchType.LAZY)
    private List<OrderVt> orderVts;
}
