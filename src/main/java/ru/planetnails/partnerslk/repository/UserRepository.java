package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u " +
            "where u.name = ?1 and u.status <> ru.planetnails.partnerslk.model.user.UserStatus.DELETED")
    User findByName(String name);

    @Query("select u from User u " +
            "where u.id = ?1 and u.status <> ru.planetnails.partnerslk.model.user.UserStatus.DELETED")
    User findNotDeletedUser(String userId);

    @Query("select u from User u " +
            "where u.id = ?1")
    User findByUserId(String userId);

}
