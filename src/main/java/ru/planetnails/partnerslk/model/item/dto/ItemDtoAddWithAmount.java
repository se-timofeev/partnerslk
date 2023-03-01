package ru.planetnails.partnerslk.model.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDtoAddWithAmount {

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    private String description;
    private String vendorCode;
    private String descriptionHtml;
    private String groupId;
    private Integer level;
    private String countryOfOrigin;
    private Boolean isOutOfStock;
    private Integer discount;
    private Double salePriceBeforeDiscount;
    private Double retailPriceBeforeDiscount;
    private Double salePrice;
    private Double retailPrice;
    private Boolean isNovelty;
    private Long amount;
}
