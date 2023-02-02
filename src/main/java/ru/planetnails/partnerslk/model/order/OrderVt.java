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

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;
    private Long n_row;
    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "item_id")
    private Item item;
    private Long amount;
    private Double sale;
    private Integer discount;
    private Double price;
    private Double total;
}
