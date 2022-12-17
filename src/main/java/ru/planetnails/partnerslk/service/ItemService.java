package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.item.Dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.Dto.ItemDtoOut;

import java.util.List;

public interface ItemService {
    ItemAddDto add(ItemAddDto itemAddDto);

    void add(List<ItemAddDto> items);

    List<ItemDtoOut> getAll();
}
