package ru.planetnails.partnerslk.broker;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.service.OrderService;

@Slf4j
@Service
@EnableRabbit
public class RabbitMQConsumer {

    private final OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    public RabbitMQConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "receive")
    public void consume(String message) {
        LOGGER.info(String.format("Received message -> %s", message));
        try {
            orderService.rabbitUpdate(message);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            log.info("The message can not de convert to OrderRabbitAddDto");
        }
    }
}
