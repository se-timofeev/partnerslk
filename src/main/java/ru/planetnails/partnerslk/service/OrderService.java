package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.order.dto.OrderAddDto;

public interface OrderService {
    String add(OrderAddDto orderAddDto);
}
