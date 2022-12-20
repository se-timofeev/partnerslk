package ru.planetnails.partnerslk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.model.role.Role;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.model.user.UserStatus;
import ru.planetnails.partnerslk.repository.RoleRepository;
import ru.planetnails.partnerslk.repository.UserRepository;
import ru.planetnails.partnerslk.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(UserStatus.ACTIVE);
        User registerUser = userRepository.save(user);
        log.info("register - user {} succefully reg.", registerUser);
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("Get all {}", users.size());
        return users;
    }

    @Override
    public User findByName(String name) {
        User user = userRepository.findByName(name);
        log.info("user with name {} found", name);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        log.info("user with name {} found", id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("delete user by id {}", id);
    }
}

