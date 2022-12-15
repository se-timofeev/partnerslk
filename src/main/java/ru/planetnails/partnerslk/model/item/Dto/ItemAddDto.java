package ru.planetnails.partnerslk.model.item.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItemAddDto {
    @NotBlank
    private String name;

    private String description;
    @NotBlank
    private String vendorCode;

    @NotBlank
    private String guid1c;

    private String descriptionHtml;
    @NotBlank
    @NotNull
    private Boolean isGroup;

    private String parentId;

    private Integer level;

    private String countryOfOrigin;

    private Boolean isOutOfStock;

}
