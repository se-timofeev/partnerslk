package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.image.dto.ImageDtoIn;
import ru.planetnails.partnerslk.model.image.dto.ImageDtoOut;
import ru.planetnails.partnerslk.service.ImageService;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping(value = "/api/v1/images")
@Tag(name = "Images", description = "Вы можете создать, обновить, получить картинки к товарам")
public class ImageRestControllerV1 {

    private ImageService imageService;

    @Operation(summary = "Загрузка списка картинок")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Картинки загружены",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Нет изображений для сохранения",
                    content = @Content),
    })
    @PostMapping
    public void addImages(@RequestBody @Valid List<ImageDtoIn> imageList) {
        log.info("Получен эндпоинт POST /api/v1/images, imageList = " + imageList);
        imageService.addImages(imageList);
    }

    @Operation(summary = "Получить картинку по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает ImageDtoOut",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImageDtoOut.class))}),
            @ApiResponse(responseCode = "404", description = "картинка с id = %s не найдена",
                    content = @Content),
    })
    @GetMapping("/{imageId}")
    public ImageDtoOut getImageById(@PathVariable String imageId) {
        log.info("Получен эндпоинт GET /api/v1/images/{imageId}, imageId = " + imageId);
        return imageService.getImageById(imageId);
    }

    @Operation(summary = "Удаление картинки по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Картинка удалена",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "картинка с id = %s не найдена",
                    content = @Content),
    })
    @DeleteMapping("/{imageId}")
    public void deleteImageById(@PathVariable String imageId) {
        log.info("Получен эндпоинт DELETE /api/v1/images/{imageId}, imageId = " + imageId);
        imageService.deleteImageById(imageId);
    }

    @Operation(summary = "Получить несколько картинок по по itemId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает List<ImageDtoOut>",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ImageDtoOut.class)))}),
            @ApiResponse(responseCode = "404", description = "картинка с itemId = %s не найдена",
                    content = @Content),
    })
    @GetMapping("/getByItemId/{itemId}")
    public Page<ImageDtoOut> getImageByItemId(@PathVariable String itemId,
                                              @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен эндпоинт GET /api/v1/images/{itemId}, itemId = " + itemId);
        return imageService.getImageByItemId(itemId, from, size);
    }
}
