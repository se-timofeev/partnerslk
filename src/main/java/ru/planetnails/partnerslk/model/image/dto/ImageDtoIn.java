package ru.planetnails.partnerslk.model.image.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;


@Validated
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class ImageDtoIn {

    @NonNull @NotBlank
    @Size(max = 36)
    private String id;
    @NonNull @NotBlank
    private String base64;
    @NonNull @NotBlank @Size(max = 50)
    private String itemId;


}
