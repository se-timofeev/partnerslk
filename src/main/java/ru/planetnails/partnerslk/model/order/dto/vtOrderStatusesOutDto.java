package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.planetnails.partnerslk.model.order.OrderStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class vtOrderStatusesOutDto {

    private Long id;
    private String orderId;
    private OrderStatus orderStatus;
    private LocalDateTime updated;
    private String userId;
}
