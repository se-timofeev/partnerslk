package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSecondPartOutDto {

    private List<OderVtOutDto> orderVts;

}
