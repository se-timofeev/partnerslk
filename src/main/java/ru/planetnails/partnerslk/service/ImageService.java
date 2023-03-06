package ru.planetnails.partnerslk.service;

import org.springframework.data.domain.Page;
import ru.planetnails.partnerslk.model.image.dto.ImageDtoIn;
import ru.planetnails.partnerslk.model.image.dto.ImageDtoOut;

import java.util.List;

public interface ImageService {
    void addImages(List<ImageDtoIn> imageList);

    ImageDtoOut getImageById(String imageId);

    void deleteImageById(String imageId);

    Page<ImageDtoOut> getImageByItemId(String itemId, Integer from, Integer size);
}
