package ru.planetnails.partnerslk.service;

import org.springframework.data.domain.Page;
import ru.planetnails.partnerslk.model.item.queryParams.GetItemsParams;
import ru.planetnails.partnerslk.model.item.queryParams.ItemQueryParams;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.GroupDtoOut;

import java.util.List;

public interface ItemService {

    void add(List<ItemAddDto> items);

    List<GroupDtoOut> getFilteredGroups(Integer level, String groupId, Integer from, Integer size);

    Page<ItemDtoOut> getItems(GetItemsParams params);

    List<ItemDtoOut> getItemByParams(String partnerId, ItemQueryParams params, Integer from, Integer size);

    void deleteItems(List<String> itemsId);

    ItemDtoOut getItemById(String itemId, String partnerId);
}
