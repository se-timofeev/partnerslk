package ru.planetnails.partnerslk.model.order.dto;

import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderVt;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;
import ru.planetnails.partnerslk.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order fromOrderAddDtoOrder(OrderAddDto orderAddDto, Contractor contractor, List<OrderVt> orderVts,
                                             List<vtOrderStatuses> vtOrderStatuses) {
        return new Order(
                orderAddDto.getNum(),
                LocalDateTime.now(),
                orderAddDto.getSumWithoutDiscount(),
                orderAddDto.getSumOfDiscount(),
                orderAddDto.getSumWithDiscount(),
                contractor,
                orderAddDto.getStatus(),
                orderVts,
                vtOrderStatuses
        );
    }

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

    public static OrderVt fromOrderVtAddDtoToOrderVt(OderVtAddDto oderVtAddDto, Item item) {
        return new OrderVt(
                oderVtAddDto.getN_row(),
                item,
                oderVtAddDto.getAmount(),
                oderVtAddDto.getSale(),
                oderVtAddDto.getDiscount(),
                oderVtAddDto.getPrice(),
                oderVtAddDto.getTotal()
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

    public static vtOrderStatuses fromVtOrderStatusesAddDtoToVtOrderStatuses (vtOrderStatusesAddDto vtOrderStatusesAddDto, User user){
        return new vtOrderStatuses(
                vtOrderStatusesAddDto.getOrderStatus(),
                LocalDateTime.now(),
                user
        );
    }
}
