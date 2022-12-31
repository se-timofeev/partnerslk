package ru.planetnails.partnerslk.model.contractor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.planetnails.partnerslk.model.contractor.StatusContractor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractorAddDto {
    @Schema(description = "id контрагента")
    @Size(max = 50)
    private String id;

    @Schema(description = "Краткое наименование")
    @Size(max = 100)
    private String name;

    @Schema(description = "Описание")
    @Size(max = 255)
    private String description;

    @Schema(description = "ИНН")
    @Size(max = 12)
    private String inn;

    @Schema(description = "КПП")
    @Size(max = 9)
    private String kpp;

    @Schema(description = "Юр. адрес")
    @Size(max = 255)
    private String legalAddress;

    @Schema(description = "Факт. адрес")
    @Size(max = 255)
    private String actualAddress;

    @Schema(description = "ID партнера (обязательный)")
    @Size(max = 50)
    @NotBlank
    @NonNull
    private String partnerId;

    @Schema(description = "Статус ACTIVE or BLOCKED")
    @Size(max = 50)
    private StatusContractor status;

}
