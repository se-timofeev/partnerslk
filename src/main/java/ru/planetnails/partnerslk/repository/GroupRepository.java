package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.group.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
