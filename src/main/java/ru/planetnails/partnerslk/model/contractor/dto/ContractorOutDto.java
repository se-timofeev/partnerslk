package ru.planetnails.partnerslk.model.contractor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ContractorOutDto {

    @Schema(description = "id контрагента")
    private String id;

    @Schema(description = "Краткое наименование")
    private String name;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "ИНН")
    private String inn;

    @Schema(description = "КПП")
    private String kpp;

    @Schema(description = "Юр. адрес")
    private String legalAddress;

    @Schema(description = "Факт. адрес")
    private String actualAddress;
}
