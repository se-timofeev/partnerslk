package ru.planetnails.partnerslk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.model.user.UserStatus;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String status;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setName(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus(UserStatus.valueOf(status));
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getName());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setStatus(user.getStatus().name());
        return adminUserDto;
    }
}
