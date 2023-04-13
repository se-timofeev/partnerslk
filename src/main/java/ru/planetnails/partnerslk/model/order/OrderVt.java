package ru.planetnails.partnerslk.model.order;

import lombok.*;
import ru.planetnails.partnerslk.model.item.Item;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders_vt")
public class OrderVt {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;
    private Long nRow;
    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "item_id")
    private Item item;
    private Long amount;
    private Double sale;
    private Integer discount;
    private Double price;
    private Double total;

    public OrderVt(Long nRow, Item item, Long amount, Double sale, Integer discount, Double price, Double total) {
        this.nRow = nRow;
        this.item = item;
        this.amount = amount;
        this.sale = sale;
        this.discount = discount;
        this.price = price;
        this.total = total;
    }
}
