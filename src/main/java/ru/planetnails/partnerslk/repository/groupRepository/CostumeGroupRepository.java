package ru.planetnails.partnerslk.repository.groupRepository;

import ru.planetnails.partnerslk.model.group.Group;

import java.util.List;

public interface CostumeGroupRepository {

    List<Group> getFilteredGroups(Integer level, String groupId, Integer from, Integer size);
}
