package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class
OrderAddDto {

    @NotEmpty(message = "ContractorId may not be null or empty")
    private String contractorId;
    @NotEmpty(message = "PartnerId may not be null or empty")
    private String partnerId;
    private List<OderVtAddDto> orderVts;
    @NotEmpty(message = "UserId may not be null or empty")
    private String userId;
}
