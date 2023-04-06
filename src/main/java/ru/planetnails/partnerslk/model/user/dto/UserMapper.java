package ru.planetnails.partnerslk.model.user.dto;

import ru.planetnails.partnerslk.model.price.Price;
import ru.planetnails.partnerslk.model.price.dto.PriceAddDto;
import ru.planetnails.partnerslk.model.user.User;

import java.time.LocalDateTime;

public class UserMapper {
    public static Price fromPriceAddDtoToPrice(PriceAddDto priceAddDto) {
        return Price.builder()
                .id(priceAddDto.getId())
                .retail(priceAddDto.getRetail())
                .sale(priceAddDto.getSale())
                .updated(LocalDateTime.now())
                .build();
    }

    public static User fromUserAddDtoToUser(UserAddDto userAddDto) {
        return User.builder()
                .id(userAddDto.getId())
                .firstName(userAddDto.getFirstName())
                .lastName(userAddDto.getLastName())
                .fullName(userAddDto.getFullName())
                .mobile(userAddDto.getMobile())
                .email(userAddDto.getEmail())
                .name(userAddDto.getName())
                .updated(LocalDateTime.now()).build();
    }

    public static User fromUserAddDtoToUser(UserAddDto userAddDto, User user) {
        user.setFirstName(userAddDto.getFirstName());
        user.setLastName(userAddDto.getLastName());
        user.setFullName(userAddDto.getFullName());
        user.setMobile(userAddDto.getMobile());
        user.setEmail(userAddDto.getEmail());
        user.setUpdated(LocalDateTime.now());
        return user;

    }

    public static UserOutDto fromUserToUserOutDto(User user) {
        return UserOutDto.builder()
                .name(user.getName())
                .status(user.getStatus()).build();
    }

    public static UserFullOutDto fromUserToUserFullOutDto(User user) {
        return UserFullOutDto.builder()
                .name(user.getName())
                .id(user.getId())
                .status(user.getStatus()).build();
    }
}
