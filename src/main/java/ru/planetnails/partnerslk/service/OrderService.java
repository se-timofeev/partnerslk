package ru.planetnails.partnerslk.service;

import org.springframework.data.domain.PageRequest;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.dto.OrderAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;

import java.util.List;

public interface OrderService {
    String add(OrderAddDto orderAddDto);
    Order findById(String orderId);
    List<Order> findAllByPartnerId(String partnerId, PageRequest pageRequest);

    OrderOutDto setStatusForOrder(String orderId, String status, String userId);
}
