package ru.planetnails.partnerslk.model.item.dto;

import lombok.*;


@Data
@AllArgsConstructor
public class ItemDtoOutShort {

    private String id;
    private String name;
    private String parent_id;
    private Integer level;

}
