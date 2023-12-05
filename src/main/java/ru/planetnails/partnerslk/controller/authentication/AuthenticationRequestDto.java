package ru.planetnails.partnerslk.controller.authentication;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthenticationRequestDto {
    @Size(min = 1, max = 15)
    private String username;
    @Size(min = 8, max = 15)
    private String password;

    @Override
    public String toString() {
        return "AuthenticationRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
