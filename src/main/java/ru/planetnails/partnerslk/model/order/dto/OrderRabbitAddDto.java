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
OrderRabbitAddDto {

    @NotEmpty(message = "OrderId may not be null or empty")
    private String orderId;
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
    @NotEmpty(message = "Status may not be null or empty")
    private String status;
    @NotEmpty(message = "User may not be null or empty")
    private String user;
}
