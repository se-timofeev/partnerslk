package ru.planetnails.partnerslk.model.order.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class OderVtOutDto {

    private Long id;
    private UUID orderId;
    private Long n_row;
    private String itemId;
    private Long amount;
    private Double sale;
    private Integer discount;
    private Double price;
    private Double total;
}
