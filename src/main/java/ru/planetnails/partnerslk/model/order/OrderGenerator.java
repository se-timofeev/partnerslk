package ru.planetnails.partnerslk.model.order;


import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;

public interface OrderGenerator {

    OrderOutDto setStatusForOrderUser(String orderId, String user);

    Order setStatusForOrderManager(String orderId, String user);
}
