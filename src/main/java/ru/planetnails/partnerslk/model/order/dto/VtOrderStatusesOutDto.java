package ru.planetnails.partnerslk.model.order.dto;

import lombok.*;
import ru.planetnails.partnerslk.model.order.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VtOrderStatusesOutDto {

    private Long id;
    private String orderId;
    private OrderStatus orderStatus;
    private LocalDateTime updated;
    private String userId;

    public VtOrderStatusesOutDto(Long id, OrderStatus orderStatus, LocalDateTime updated) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.updated = updated;
    }
}
