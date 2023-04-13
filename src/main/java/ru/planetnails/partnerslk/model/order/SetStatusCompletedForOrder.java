package ru.planetnails.partnerslk.model.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.planetnails.partnerslk.model.order.dto.OrderMapper;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;
import ru.planetnails.partnerslk.repository.OrderRepository;

import java.time.LocalDateTime;

@Slf4j
@Component("COMPLETED")
public class SetStatusCompletedForOrder implements OrderGenerator {

    private final OrderRepository orderRepository;

    @Autowired
    public SetStatusCompletedForOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderOutDto setStatusForOrder(String orderId, String userId) {
        Order order = SetStatusApprovedForOrder.validation(orderId, userId, orderRepository, log);
        order.setStatus(OrderStatus.COMPLETED);
        VtOrderStatuses vtOrderStatuses = new VtOrderStatuses(
                OrderStatus.COMPLETED,
                LocalDateTime.now(),
                order.getVtOrderStatuses().get(0).getUser()
        );
        vtOrderStatuses.setOrder(order);
        order.getVtOrderStatuses().add(vtOrderStatuses);

        return OrderMapper.fromOrderToOrderOutDto(orderRepository.save(order));
    }
}
