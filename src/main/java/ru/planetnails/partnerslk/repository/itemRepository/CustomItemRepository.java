package ru.planetnails.partnerslk.repository.itemRepository;

import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.item.ItemQueryParams;

import java.util.List;

public interface CustomItemRepository {

    List<Item> getItemByParams(ItemQueryParams params, Integer from, Integer size);

}
