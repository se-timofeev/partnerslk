package ru.planetnails.partnerslk.model.user.dto;

import lombok.*;
import ru.planetnails.partnerslk.model.user.UserStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserFullOutDto {

    private String name;
    private String id;
    private UserStatus status;
}
