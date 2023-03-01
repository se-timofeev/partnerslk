package ru.planetnails.partnerslk.model.image.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class ImageDtoIn {

    @NonNull @NotBlank @Size(max = 36)
    private String id;
    @NonNull @NotBlank
    private String base64;
    @NonNull @NotBlank @Size(max = 50)
    private String itemId;


}
