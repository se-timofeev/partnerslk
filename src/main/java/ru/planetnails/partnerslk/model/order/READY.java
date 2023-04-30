package ru.planetnails.partnerslk.model.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.order.dto.OrderMapper;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;
import ru.planetnails.partnerslk.repository.OrderRepository;

import java.time.LocalDateTime;

@Slf4j
@Component("READY")
public class READY implements OrderGenerator {

    private final OrderRepository orderRepository;

    @Autowired
    public READY(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderOutDto setStatusForOrderUser(String orderId, String user) {
        Order order = APPROVED.validation(orderId, user, orderRepository, log);
        order.setStatus(OrderStatus.READY);
        VtOrderStatuses vtOrderStatuses = new VtOrderStatuses(
                OrderStatus.READY,
                LocalDateTime.now(),
                user
        );
        vtOrderStatuses.setOrder(order);
        order.getVtOrderStatuses().add(vtOrderStatuses);

        return OrderMapper.fromOrderToOrderOutDto(orderRepository.save(order));
    }

    @Override
    public Order setStatusForOrderManager(String orderId, String user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        log.info("Order with id {} found", orderId);
        order.setStatus(OrderStatus.READY);
        VtOrderStatuses vtOrderStatuses = new VtOrderStatuses(
                OrderStatus.READY,
                LocalDateTime.now(),
                user
        );
        vtOrderStatuses.setOrder(order);
        order.getVtOrderStatuses().add(vtOrderStatuses);

        return order;
    }
}
