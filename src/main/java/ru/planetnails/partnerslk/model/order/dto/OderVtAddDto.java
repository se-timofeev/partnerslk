package ru.planetnails.partnerslk.model.order.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OderVtAddDto {

    private Long n_row;
    private String itemId;
    private Long amount;
    private Double sale;
    private Integer discount;
    private Double price;
    private Double total;
}
