package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.item.dto.ItemDtoAddWithAmount;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;

import java.util.List;

public interface OrderService {

    OrderOutDto add(List<ItemDtoAddWithAmount> items, String userId);
}
