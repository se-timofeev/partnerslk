package ru.planetnails.partnerslk.model.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
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
    @NotEmpty(message = "UserId may not be null or empty")
    private String userId;
}
