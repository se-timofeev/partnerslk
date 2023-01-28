package ru.planetnails.partnerslk.model.item.dto;

import lombok.*;


@Data
@AllArgsConstructor
public class GroupDtoOut {

    private String id;
    private String name;
    private String groupId;
    private Integer level;
}
