package ru.planetnails.partnerslk.model.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.repository.OrderRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@Slf4j
@Component("APPROVED")
public class APPROVED implements OrderGenerator {

    private final OrderRepository orderRepository;

    public APPROVED(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Order setStatusForOrderUser(String orderId, String user) {
        Order order = validation(orderId, user, orderRepository, log);
        order.setStatus(OrderStatus.APPROVED);
        VtOrderStatuses vtOrderStatuses = new VtOrderStatuses(
                OrderStatus.APPROVED,
                LocalDateTime.now(),
                user
        );
        vtOrderStatuses.setOrder(order);
        order.getVtOrderStatuses().add(vtOrderStatuses);

        return order;
    }

    @Override
    public Order setStatusForOrderManager(String orderId, String user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        log.info("Order with id {} found", orderId);
        order.setStatus(OrderStatus.APPROVED);
        VtOrderStatuses vtOrderStatuses = new VtOrderStatuses(
                OrderStatus.APPROVED,
                LocalDateTime.now(),
                user
        );
        vtOrderStatuses.setOrder(order);
        order.getVtOrderStatuses().add(vtOrderStatuses);

        return order;
    }

    protected static Order validation(String orderId, String user, OrderRepository orderRepository, Logger log) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        log.info("Order with id {} found", orderId);
        if (!user.equals(order.getVtOrderStatuses().get(0).getUser())) {
            log.error("Order doesn't belong to User with id {}", user);
            throw new ValidationException("Order doesn't belong to this User");
        }
        return order;
    }
}
