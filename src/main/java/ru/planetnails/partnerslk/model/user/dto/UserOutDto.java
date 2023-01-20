package ru.planetnails.partnerslk.model.user.dto;

import lombok.*;
import ru.planetnails.partnerslk.model.user.UserStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserOutDto {

    private String name;
    private UserStatus status;
}
