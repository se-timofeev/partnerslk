package ru.planetnails.partnerslk.model.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserAddDto {

    @NotBlank
    private String id;
    private String partnerId;
    @NotBlank
    private String fullName;
    @NotBlank
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    @Size(min = 5, max = 15)
    private String name;
    @Size(min = 6, max = 20)
    private String password;
}
