package ru.planetnails.partnerslk.model.image.dto;

import ru.planetnails.partnerslk.model.image.Image;

public class ImageMapping {

    public static Image toImage(ImageDtoIn imageDtoIn) {
        return Image.builder()
                .id(imageDtoIn.getId())
                .base64(imageDtoIn.getBase64())
                .itemId(imageDtoIn.getItemId())
                .build();
    }

    public static ImageDtoOut toImageDtoOut(Image image) {
        return ImageDtoOut.builder()
                .id(image.getId())
                .base64(image.getBase64())
                .itemId(image.getItemId())
                .build();
    }
}
