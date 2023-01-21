package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByName(String name);

}
