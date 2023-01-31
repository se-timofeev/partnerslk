package ru.planetnails.partnerslk.repository.groupRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.group.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, CostumeGroupRepository {

    @Query("select g.id from Group as g where g.groupId = ?1")
    List<String> findIdListGroupId(String groupId);
}
