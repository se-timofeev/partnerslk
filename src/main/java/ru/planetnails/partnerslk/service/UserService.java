package ru.planetnails.partnerslk.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.model.user.dto.UserAddDto;
import ru.planetnails.partnerslk.model.user.dto.UserOutDto;

import java.util.List;

public interface UserService {

    UserOutDto add(UserAddDto userAddDto);


    User findByName(String name);

    User findById(String id);

    User findByUsername(String username);

    void delete(String id);

    UserOutDto setUserActive(String userId);

    UserOutDto setUserBlocked(String userId);

    UserOutDto getUser(String userId);

    UserOutDto setUserPending(String userId);

    void deleteUser(String userId, HttpServletRequest request);

    List<User> findAllUsers();
}
