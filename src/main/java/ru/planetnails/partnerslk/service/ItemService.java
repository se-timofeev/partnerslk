package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;

import java.util.List;

public interface ItemService {
    ItemAddDto add(ItemAddDto itemAddDto);

    void add(List<ItemAddDto> items);

    List<ItemDtoOut> getAll();
}
