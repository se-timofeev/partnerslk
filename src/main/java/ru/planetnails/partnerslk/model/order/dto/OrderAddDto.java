package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.planetnails.partnerslk.model.order.OrderStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddDto {

    private Long num;
    private Double sumWithoutDiscount;
    private Double sumOfDiscount;
    private Double sumWithDiscount;
    private String contractorId;
    private OrderStatus Status;
    private List<OderVtAddDto> orderVts;
    private List<vtOrderStatusesAddDto> vtOrderStatuses;
}
