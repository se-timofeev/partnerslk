package ru.planetnails.partnerslk.model.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.repository.OrderRepository;

import java.time.LocalDateTime;

@Slf4j
@Component("WORKHOUSE")
public class WORKHOUSE implements OrderGenerator {

    private final OrderRepository orderRepository;

    @Autowired
    public WORKHOUSE(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order setStatusForOrderManager(String orderId, String user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        log.info("Order with id {} found", orderId);
        order.setStatus(OrderStatus.WORKHOUSE);
        VtOrderStatuses vtOrderStatuses = new VtOrderStatuses(
                OrderStatus.WORKHOUSE,
                LocalDateTime.now(),
                user
        );
        vtOrderStatuses.setOrder(order);
        order.getVtOrderStatuses().add(vtOrderStatuses);

        return order;
    }

    @Override
    public Order setStatusForOrderUser(String orderId, String user) {
        return null;
    }
}
