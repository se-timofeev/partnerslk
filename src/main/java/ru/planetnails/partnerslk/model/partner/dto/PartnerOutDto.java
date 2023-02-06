package ru.planetnails.partnerslk.model.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(description = "DTO для GET запроса")
public class PartnerOutDto {
    @NotBlank
    @Schema(description = "id партнёра")
    @Size(max = 50)
    private String id;

    @NotBlank
    @Schema(description = "Наименование партнёра")
    @Size(max = 255)
    private String name;

    @NotBlank
    @Schema(description = "Процент скидки")
    private int discount;

    @NotBlank
    @Schema(description = "Ответственный менеджер")
    @Size(max = 100)
    private String account;
}
