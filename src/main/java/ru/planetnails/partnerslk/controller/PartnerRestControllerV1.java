package ru.planetnails.partnerslk.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.partner.dto.PartnerAddDto;
import ru.planetnails.partnerslk.service.PartnerService;

@RestController
@Validated
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api/v1/partners")
public class PartnerRestControllerV1 {
    PartnerService partnerService;

    @PostMapping
    @PutMapping
    public String add(@RequestBody PartnerAddDto partnerAddDto) {
        partnerService.add(partnerAddDto);
        return "Your data has been queued.";
    }
}
