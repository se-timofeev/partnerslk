package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.planetnails.partnerslk.model.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutDto {

    private String id;
    private Long num;
    private LocalDateTime orderDate;
    private Double sumWithoutDiscount;
    private Double sumOfDiscount;
    private Double sumWithDiscount;
    private String contractorId;
    private OrderStatus Status;
    private List<OderVtOutDto> orderVts;
    private List<vtOrderStatusesOutDto> vtOrderStatuses;
}
