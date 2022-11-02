package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.user.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByName(String name);

    User findById(Long id);

    void delete(Long id);

}
