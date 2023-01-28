package ru.planetnails.partnerslk.model.group;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto {

    private String id;

    @NonNull @NotBlank
    private String name;

    private String parentId;

    @NonNull
    private Integer level;
}
