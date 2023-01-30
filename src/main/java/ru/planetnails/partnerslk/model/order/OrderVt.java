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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long n_row;
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private Long amount;
    private Double sale;
    private Integer discount;
    private Double price;
    private Double total;
}
