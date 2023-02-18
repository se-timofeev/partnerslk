package ru.planetnails.partnerslk.model.order.dto;

import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderVt;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderOutDto fromOrderToOrderOutDto(Order order) {
        return new OrderOutDto(
                order.getId(),
                order.getNum(),
                order.getOrderDate(),
                order.getSumWithoutDiscount(),
                order.getSumWithoutDiscount(),
                order.getSumWithDiscount(),
                order.getContractor(),
                order.getStatus(),
                order.getOrderVts().stream()
                        .map(OrderMapper::fromOderVtToOrderOutDto)
                        .collect(Collectors.toList()),
                order.getVtOrderStatuses().stream()
                        .map(OrderMapper::fromVtOrderStatusesToOrderStatusesOutDto)
                        .collect(Collectors.toList())
        );
    }

    public static OderVtOutDto fromOderVtToOrderOutDto(OrderVt orderVt) {
        return new OderVtOutDto(
                orderVt.getId(),
                orderVt.getOrder().getId(),
                orderVt.getN_row(),
                orderVt.getItem().getId(),
                orderVt.getAmount(),
                orderVt.getSale(),
                orderVt.getDiscount(),
                orderVt.getPrice(),
                orderVt.getTotal()
        );
    }

    public static vtOrderStatusesOutDto fromVtOrderStatusesToOrderStatusesOutDto(vtOrderStatuses vtOrderStatuses) {
        return new vtOrderStatusesOutDto(
                vtOrderStatuses.getId(),
                vtOrderStatuses.getOrder().getId(),
                vtOrderStatuses.getOrderStatus(),
                vtOrderStatuses.getUpdated(),
                vtOrderStatuses.getUser().getId()
        );
    }
}
