package ru.planetnails.partnerslk.model.group;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;


@Validated
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
