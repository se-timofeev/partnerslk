package ru.planetnails.partnerslk.model.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OderVtAddDto {

    private Long nRow;
    private String itemId;
    private Long amount;
    private Double sale;
    private Integer discount;
    private Double price;
    private Double total;
}
