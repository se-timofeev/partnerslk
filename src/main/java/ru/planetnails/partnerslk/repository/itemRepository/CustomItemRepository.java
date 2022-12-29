package ru.planetnails.partnerslk.repository.itemRepository;

import ru.planetnails.partnerslk.model.item.Item;

import java.util.List;

public interface CustomItemRepository {

    List<Item> getFilteredGroups(Integer level, String parentId);

    List<Item> getFilteredItems(String groupId);
}
