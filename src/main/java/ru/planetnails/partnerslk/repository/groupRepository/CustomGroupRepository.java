package ru.planetnails.partnerslk.repository.groupRepository;

import ru.planetnails.partnerslk.model.group.Group;

import java.util.List;

public interface CustomGroupRepository  {

    List<Group> getFilteredGroups(Integer level, String groupId, Integer from, Integer size);

     List<Group> getRootGroups();

    List<Group> getChildGroups (Group group);
}
