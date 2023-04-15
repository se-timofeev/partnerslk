package ru.planetnails.partnerslk.controller.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
