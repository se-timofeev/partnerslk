package ru.planetnails.partnerslk.model.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ImageDtoOut {

    private String id;
    private String base64;
    private String itemId;
}
