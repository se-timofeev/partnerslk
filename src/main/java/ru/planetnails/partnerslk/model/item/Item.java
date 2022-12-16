package ru.planetnails.partnerslk.model.item;

import lombok.*;
import ru.planetnails.partnerslk.model.baseClasses.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="items")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "guid1c")
    private String guid1c;

    @Column(name="description_html")
    private String descriptionHtml;

    @Column(name="is_group")
    private Boolean isGroup;

    @Column (name="parent_guid1c")
    private String parentGuid1c;

    @Column (name="level")
    private Integer level;

    @Column (name="country")
    private String countryOfOrigin;

    @Column(name="is_out_of_stock")
    private Boolean isOutOfStock;


}
