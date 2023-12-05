package ru.planetnails.partnerslk.model.price.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;


@Validated
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
