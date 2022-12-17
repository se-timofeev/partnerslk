package ru.planetnails.partnerslk.model.item;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(name="is_group")
    private Boolean isGroup;

    @Column (name="parent_id")
    private String parentId;

    @Column (name="level")
    private Integer level;

    @Column (name="country")
    private String countryOfOrigin;

    @Column(name="is_out_of_stock")
    private Boolean isOutOfStock;

    @Column(name="updated")
    private LocalDateTime updated;


}
