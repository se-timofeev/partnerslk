package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.planetnails.partnerslk.model.order.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class vtOrderStatusesAddDto {

    private OrderStatus orderStatus;
    private String userId;
}
