package ru.planetnails.partnerslk.broker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("main","1C", message);
      //  rabbitTemplate.receive("main");
    }
}