package ru.planetnails.partnerslk.service;

import org.springframework.data.domain.Page;
import ru.planetnails.partnerslk.model.item.ItemQueryParams;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.GroupDtoOut;

import java.util.List;

public interface ItemService {

    void add(List<ItemAddDto> items);

    List<GroupDtoOut> getFilteredGroups(Integer level, String groupId, Integer from, Integer size);

    Page<ItemDtoOut> getItemsByGroupId(String groupId, Integer from, Integer size, String partnerId);

    List<ItemDtoOut> getItemByParams(String partnerId, ItemQueryParams params, Integer from, Integer size);

}
