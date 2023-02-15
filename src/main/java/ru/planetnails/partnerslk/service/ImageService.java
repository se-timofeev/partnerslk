package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.image.dto.ImageDtoIn;
import ru.planetnails.partnerslk.model.image.dto.ImageDtoOut;

import java.util.List;

public interface ImageService {
    void addImages(List<ImageDtoIn> imageList);

    ImageDtoOut getImageById(String imageId);

    void deleteImageById(String imageId);

    List<ImageDtoOut> getImageByItemId(String itemId);
}
