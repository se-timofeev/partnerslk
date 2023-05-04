package ru.planetnails.partnerslk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.dto.OrderAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderAddUpdateDto;
import ru.planetnails.partnerslk.model.order.dto.OrderOutPartnerDto;

public interface OrderService {
    String add(OrderAddDto orderAddDto);

    Order findById(String orderId);

    OrderOutPartnerDto findAllByPartnerId(String partnerId, PageRequest pageRequest);

    Order statusForOrderUser(String orderId, String status, String userId);

    Order statusForOrderManager(String orderId, String status, String userId);

    String update(OrderAddUpdateDto orderAddUpdateDtoDto, String orderId);

    void rabbitUpdate(String message) throws JsonProcessingException;
}
