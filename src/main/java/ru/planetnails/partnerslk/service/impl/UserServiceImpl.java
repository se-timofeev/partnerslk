package ru.planetnails.partnerslk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.exception.BadRequestException;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.exception.PartnerNotFoundException;
import ru.planetnails.partnerslk.exception.UsersNameIsAlreadyTaken;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.model.role.Role;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.model.user.UserStatus;
import ru.planetnails.partnerslk.model.user.dto.UserAddDto;
import ru.planetnails.partnerslk.model.user.dto.UserMapper;
import ru.planetnails.partnerslk.model.user.dto.UserOutDto;
import ru.planetnails.partnerslk.repository.PartnerRepository;
import ru.planetnails.partnerslk.repository.RoleRepository;
import ru.planetnails.partnerslk.repository.UserRepository;
import ru.planetnails.partnerslk.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           PartnerRepository partnerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.partnerRepository = partnerRepository;
    }

    @Override
    public UserOutDto add(UserAddDto userAddDto) {
        Role roleUser = roleRepository.findByName("USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        User user;
        user = userRepository.findById(userAddDto.getId()).orElse(null);
        User user1 = userRepository.findByName(userAddDto.getName());
        if (user == null & user1 == null) {
            user = UserMapper.fromUserAddDtoToUser(userAddDto);
            user.setCreated(LocalDateTime.now());
        } else if (user == null & user1 != null) {
            throw new UsersNameIsAlreadyTaken("Users  name is already taken");
        } else {
            user = UserMapper.fromUserAddDtoToUser(userAddDto, user);
        }
        if (userAddDto.getPartnerId() != null) {
            Partner partner = partnerRepository.findById(userAddDto.getPartnerId())
                    .orElseThrow(() -> new PartnerNotFoundException("Partner id not found"));
            user.setPartner(partner);
        }
        user.setPassword(passwordEncoder.encode(userAddDto.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(UserStatus.PENDING);
        User registerUser = userRepository.save(user);
        log.info("register - user {} succefully reg.", registerUser);
        return UserMapper.fromUserToUserOutDto(user);
    }

    @Override
    public User findByName(String name) {
        User user = userRepository.findByName(name);
        if(user == null) throw new NotFoundException("User not found");
        log.info("user with name {} found", name);
        return user;
    }

    @Override
    public User findById(String userId) {
        User user = userRepository.findNotDeletedUser(userId);
        if(user == null) throw new NotFoundException("User not found");
        log.info("user with name {} found", userId);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByName(username);
        if(user == null) throw new NotFoundException("User not found");
        log.info("user with name {} found", username);
        return user;
    }

    @Override
    @Transactional
    public void delete(String userId) {
        userRepository.deleteById(userId);
        log.info("delete user by id {}", userId);
    }

    @Override
    @Transactional
    public UserOutDto setUserActive(String userId) {
        User user = findById(userId);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return UserMapper.fromUserToUserOutDto(user);

    }

    @Override
    @Transactional
    public UserOutDto setUserBlocked(String userId) {
        User user = findById(userId);
        user.setStatus(UserStatus.BLOCKED);
        userRepository.save(user);
        return UserMapper.fromUserToUserOutDto(user);
    }

    @Override
    @Transactional
    public UserOutDto setUserPending(String userId) {
        User user = findById(userId);
        user.setStatus(UserStatus.PENDING);
        userRepository.save(user);
        return UserMapper.fromUserToUserOutDto(user);
    }

    @Override
    @Transactional
    public UserOutDto getUser(String userId) {
        return UserMapper.fromUserToUserOutDto(userRepository.findByName(userId));
    }

    @Override
    public void deleteUser(String idForDelete, String requesterId) {
        if(idForDelete.equals(requesterId)) throw new BadRequestException("Пользователь не может удалить сам себя");
        User user = findById(idForDelete);
        if(user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("ADMIN")))
            throw new BadRequestException("Нельзя удалить пользователя с уровнем доступа (Role) = ADMIN");
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }
}

