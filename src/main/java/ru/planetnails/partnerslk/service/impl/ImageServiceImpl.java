package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.exception.BadRequestException;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.image.Image;
import ru.planetnails.partnerslk.model.image.dto.ImageDtoIn;
import ru.planetnails.partnerslk.model.image.dto.ImageDtoOut;
import ru.planetnails.partnerslk.model.image.dto.ImageMapping;
import ru.planetnails.partnerslk.repository.ImageRepository;
import ru.planetnails.partnerslk.service.ImageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;
    @Override
    public void addImages(List<ImageDtoIn> imageList) {
        if (imageList.size() < 1) throw new BadRequestException("Нет изображений для сохранения");
        imageRepository.saveAll(imageList.stream().map(ImageMapping::toImage).collect(Collectors.toList()));
    }

    @Override
    public ImageDtoOut getImageById(String imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> new NotFoundException(String.format("картинка с id = %s не найдена", imageId)));
        return ImageMapping.toImageDtoOut(image);
    }

    @Override
    public void deleteImageById(String imageId) {
        if(!imageRepository.existsById(imageId))
            throw new NotFoundException(String.format("картинка с id = %s не найдена", imageId));
        imageRepository.deleteById(imageId);
    }

    @Override
    public List<ImageDtoOut> getImageByItemId(String itemId) {
        List<Image> images = imageRepository.findByItemId(itemId);
        if(images.size() < 1) throw new NotFoundException(String.format("картинка с itemId = %s не найдена", itemId));
        return images.stream().map(ImageMapping::toImageDtoOut).collect(Collectors.toList());
    }
}
