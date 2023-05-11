package ru.planetnails.partnerslk.model.order;

public interface OrderGenerator {

    Order setStatusForOrderUser(String orderId, String user);

    Order setStatusForOrderManager(String orderId, String user);
}
