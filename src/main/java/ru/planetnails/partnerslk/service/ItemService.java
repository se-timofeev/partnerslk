package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.item.ItemQueryParams;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOutGroups;

import java.util.List;

public interface ItemService {
    ItemAddDto add(ItemAddDto itemAddDto);

    void add(List<ItemAddDto> items);

    List<ItemDtoOutGroups> getFilteredGroups(Integer level, String parentId, Integer from, Integer size);

    List<ItemDtoOut> getFilteredItems(String parentId, Integer from, Integer size, String partnerId);

    List<ItemDtoOut> getItemByParams(String partnerId, ItemQueryParams params, Integer from, Integer size);

    List<ItemDtoOut> getItemsPrices(String userId, String parentId, String partnerId, Integer from, Integer size);
}
