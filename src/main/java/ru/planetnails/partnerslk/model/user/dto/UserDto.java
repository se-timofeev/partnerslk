package ru.planetnails.partnerslk.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.planetnails.partnerslk.model.user.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setName(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}