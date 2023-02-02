package ru.planetnails.partnerslk.model.item;

import lombok.*;
import ru.planetnails.partnerslk.model.price.Price;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item  {

    @Id
    private String id;
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name="description_html")
    private String descriptionHtml;

    @Column (name="group_id")
    private String groupId;

    @Column (name="level")
    private Integer level;

    @Column (name="country")
    private String countryOfOrigin;

    @Column(name="is_out_of_stock")
    private Boolean isOutOfStock;

    @Column(name="updated")
    private LocalDateTime updated;

    @OneToOne (mappedBy="item")
    private Price price;

    @Column(name = "is_novelty")
    private Boolean isNovelty;

    public Item(String id) {
        this.id = id;
    }
}
