package ru.planetnails.partnerslk.broker;

public interface RabbitMQProducerService {

    void sendMessage(String message);
}
