package ru.planetnails.partnerslk.model.partner.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PartnerAddDto {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private int discount;

    private String account;
}
