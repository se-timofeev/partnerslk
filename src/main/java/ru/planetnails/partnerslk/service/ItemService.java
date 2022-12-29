package ru.planetnails.partnerslk.service;

import org.springframework.data.domain.PageRequest;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOutGroups;

import java.util.List;

public interface ItemService {
    ItemAddDto add(ItemAddDto itemAddDto);

    void add(List<ItemAddDto> items);

    List<ItemDtoOut> getFilteredItems(String groupId, PageRequest pageRequest);

    List<ItemDtoOutGroups> getFilteredGroups(Integer level, String parentId);


}
