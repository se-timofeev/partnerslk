package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddDto {

    private Double sumWithoutDiscount;
    private Double sumOfDiscount;
    private Double sumWithDiscount;
    private String contractorId;
    private List<OderVtAddDto> orderVts;
    private List<vtOrderStatusesAddDto> vtOrderStatuses;
}
