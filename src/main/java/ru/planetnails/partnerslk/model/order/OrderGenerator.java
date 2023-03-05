package ru.planetnails.partnerslk.model.order;


import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;

public interface OrderGenerator {

    OrderOutDto setStatusForOrder(String orderId, String userId);
}
