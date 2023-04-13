package ru.planetnails.partnerslk.model.price;

import lombok.*;
import ru.planetnails.partnerslk.model.item.Item;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Price {

    @Id
    private String id;

    @Column(name = "retail")
    private double retail;

    @Column(name = "sale")
    private double sale;

    @Column(name = "updated")
    private LocalDateTime updated;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
