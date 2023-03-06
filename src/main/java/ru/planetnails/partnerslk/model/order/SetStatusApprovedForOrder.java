package ru.planetnails.partnerslk.model.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.order.dto.OrderMapper;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;
import ru.planetnails.partnerslk.repository.OrderRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@Slf4j
@Component("APPROVED")
public class SetStatusApprovedForOrder implements OrderGenerator {

    private final OrderRepository orderRepository;

    @Autowired
    public SetStatusApprovedForOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public OrderOutDto setStatusForOrder(String orderId, String userId) {
        Order order = validation(orderId, userId, orderRepository, log);
        order.setStatus(OrderStatus.APPROVED);
        vtOrderStatuses vtOrderStatuses = new vtOrderStatuses(
                OrderStatus.APPROVED,
                LocalDateTime.now(),
                order.getVtOrderStatuses().get(0).getUser()
        );
        vtOrderStatuses.setOrder(order);
        order.getVtOrderStatuses().add(vtOrderStatuses);

        return OrderMapper.fromOrderToOrderOutDto(orderRepository.save(order));
    }

   protected static Order validation(String orderId, String userId, OrderRepository orderRepository, Logger log) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        log.info("Order with id {} found", orderId);
        if(!userId.equals(order.getVtOrderStatuses().get(0).getUser().getId())){
            log.error("Order doesn't belong to User with id {}", userId);
            throw new ValidationException("Order doesn't belong to this User");
        }
        return order;
    }
}
