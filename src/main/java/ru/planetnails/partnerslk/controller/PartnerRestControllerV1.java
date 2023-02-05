package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.model.partner.dto.PartnerAddDto;
import ru.planetnails.partnerslk.service.PartnerService;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@Slf4j
@Tag(name = "Partners", description = "Сервис для создания/изменения, а также получения данных по партнёерам")
@RequestMapping(value = "/api/v1/partners")
public class PartnerRestControllerV1 {
    PartnerService partnerService;

    @PostMapping
    @PutMapping
    public String add(@RequestBody PartnerAddDto partnerAddDto) {
        partnerService.add(partnerAddDto);
        return "Your data has been queued.";
    }

    @GetMapping
    public List<Partner> getAllPartners() {
        return null;
    }
}
