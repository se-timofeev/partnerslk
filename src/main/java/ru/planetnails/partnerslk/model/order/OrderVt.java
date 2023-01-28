package ru.planetnails.partnerslk.model.order;

import lombok.*;
import ru.planetnails.partnerslk.model.item.Item;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
@Builder
@Table(name = "orders_vt")
public class OrderVt {
//
    @ManyToOne(mappedBy = "orders", fetch = FetchType.LAZY)
    @Column(name = "orderId")
    @Column(name = "n_row")
    private Long nRow;
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private Long amount;
    private Double sale;
    private Integer discount;
    private Double price;
    private Double total;
}
