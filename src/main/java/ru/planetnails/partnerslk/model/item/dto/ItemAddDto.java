package ru.planetnails.partnerslk.model.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;


@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemAddDto {
    @NotBlank
    private String id;
    @NotBlank
    private String name;

    private String description;

    private String vendorCode;

    private String descriptionHtml;

    private Boolean isGroup;

    private String parentId;

    private Integer level;

    private String countryOfOrigin;

    private Boolean isOutOfStock;

    private Boolean isNovelty;

    @Override
    public String toString() {
        return "ItemAddDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isGroup=" + isGroup +
                ", groupId='" + parentId + '\'' +
                '}';
    }
}
