package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class
OrderAddDto {

    @NotNull(message = "SumWithoutDiscount may not be null")
    private Double sumWithoutDiscount;
    @NotNull(message = "SumOfDiscount may not be null")
    private Double sumOfDiscount;
    @NotNull(message = "SumWithDiscount may not be null")
    private Double sumWithDiscount;
    @NotEmpty(message = "ContractorId may not be null or empty")
    private String contractorId;
    @NotEmpty(message = "PartnerId may not be null or empty")
    private String partnerId;
    private List<OderVtAddDto> orderVts;
    @NotNull(message = "VtOrderStatuses may not be null")
    private List<VtOrderStatusesAddDto> vtOrderStatuses;
}
