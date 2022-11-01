package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.baseClasses.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
