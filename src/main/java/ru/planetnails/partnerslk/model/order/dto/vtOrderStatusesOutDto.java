package ru.planetnails.partnerslk.model.order.dto;

import lombok.*;
import ru.planetnails.partnerslk.model.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class vtOrderStatusesOutDto {

    private Long id;
    private UUID orderId;
    private OrderStatus orderStatus;
    private LocalDateTime updated;
    private String userId;
}
