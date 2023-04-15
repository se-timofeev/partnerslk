package ru.planetnails.partnerslk.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.service.OrderService;

import javax.persistence.Access;

@Slf4j
@Service
public class RabbitMQConsumer {

    private final OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    public RabbitMQConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = {"${rabbit.queueName}"})
    public void consume(String message) {
        LOGGER.info(String.format("Received message -> %s", message));
        System.out.println("Message read from myQueue : " + message);
        try {
            orderService.rabbitUpdate(message);
        } catch (JsonProcessingException exception) {
            System.out.println(exception);
            log.info("The message can not de convert to OrderRabbitAddDto");
        }
    }
}
