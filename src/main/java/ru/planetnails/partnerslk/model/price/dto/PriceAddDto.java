package ru.planetnails.partnerslk.model.price.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceAddDto {

    @NotBlank
    private String id;
    @NotBlank
    private double retail;
    @NotBlank
    private double sale;
}
