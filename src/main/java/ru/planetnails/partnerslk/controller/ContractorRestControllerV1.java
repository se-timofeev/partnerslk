package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorAddDto;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorMapper;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorOutDto;
import ru.planetnails.partnerslk.service.ContractorService;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@Tag(name = "Contrators", description = "Сервис для создания/изменения, а также получения данных по контрагентам")
@RequestMapping(value = "/api/v1/contractors")
public class ContractorRestControllerV1 {
    private final ContractorService contractorService;

    @Operation(summary = "Получение контрагента по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Контрагент найден",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ContractorOutDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Контрагент не найден",
                    content = @Content)
    })
    @GetMapping(value = "{id}")
    public ResponseEntity<ContractorOutDto> getContractorById(@PathVariable(name = "id") String id) {
        Contractor contractor = contractorService.findById(id);
        if (contractor == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ContractorOutDto result = ContractorMapper.fromContractorToContractorOutDto(contractor);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Добавление/обновление массива контрагентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Your data has been queued (данные получены и добавлены в очередь)",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data error(ошибка валидации данных)",
                    content = @Content)
    })
    @PostMapping()
    @PutMapping
    public String add(@RequestBody List<ContractorAddDto> contractors){
        contractorService.add(contractors);
        return "Your data has been queued.";
    }

}
