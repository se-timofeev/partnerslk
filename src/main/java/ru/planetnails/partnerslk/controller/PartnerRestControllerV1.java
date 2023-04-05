package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.model.partner.dto.PartnerAddDto;
import ru.planetnails.partnerslk.model.partner.dto.PartnerMapper;
import ru.planetnails.partnerslk.model.partner.dto.PartnerOutDto;
import ru.planetnails.partnerslk.service.PartnerService;

@RestController
@Validated
@AllArgsConstructor
@Slf4j
@CrossOrigin
@Tag(name = "Partners", description = "Сервис для создания/изменения, а также получения данных по партнёрам")
@RequestMapping(value = "/api/v1/partners")
public class PartnerRestControllerV1 {
    PartnerService partnerService;

    @Operation(summary = "Добавление/обновление данных партнёра")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Your data has been queued (данные получены и добавлены в очередь)",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data error(ошибка валидации данных)",
                    content = @Content)
    })
    @PostMapping
    @PutMapping
    public String add(@RequestBody PartnerAddDto partnerAddDto) {
        partnerService.add(partnerAddDto);
        return "Your data has been queued.";
    }


    @Operation(summary = "Получение данных партнёра по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные найдены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PartnerOutDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Данные не найдены",
                    content = @Content)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<PartnerOutDto> getPartnerById(@PathVariable(name = "id") String id) {
        log.info(String.format(" GET /api/v1/partners/partner; partnerId = %s", id));
        Partner partner = partnerService.findPartnerById(id);
        if (partner == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PartnerOutDto result = PartnerMapper.fromPartnerToPartnerOutDto(partner);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Получение данных партнёра по username")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные найдены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PartnerOutDto.class))}),
            @ApiResponse(
                    responseCode = "204",
                    description = "Данные не найдены",
                    content = @Content)
    })
    @GetMapping(value = "/user/name/{username}")
    public ResponseEntity<PartnerOutDto> getPartnerByUsername(@PathVariable(name = "username") String username) {
        log.info(String.format(" GET /api/v1/partners/user/name/; username = %s", username));
        Partner partner = partnerService.findPartnerByUsername(username);
        if (partner == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PartnerOutDto result = PartnerMapper.fromPartnerToPartnerOutDto(partner);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Получение данных партнёра по user Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные найдены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PartnerOutDto.class))}),
            @ApiResponse(
                    responseCode = "204",
                    description = "Данные не найдены",
                    content = @Content)
    })
    @GetMapping(value = "/user/id/{id}")
    public ResponseEntity<PartnerOutDto> getPartnerByUserId(@PathVariable(name = "id") String id) {
        log.info(String.format(" GET /api/v1/partners/user/id; user id = %s", id));
        Partner partner = partnerService.findPartnerByUserId(id);
        if (partner == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PartnerOutDto result = PartnerMapper.fromPartnerToPartnerOutDto(partner);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Удаление информации по партнёру")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные найдены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PartnerOutDto.class))}),
            @ApiResponse(
                    responseCode = "204",
                    description = "Данные не найдены",
                    content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public void deletePartner(@PathVariable(name = "id") String id) {
        log.info(String.format(" DELETE /api/v1/partners/id; user id = %s", id));

        partnerService.delete(id);

    }
}
