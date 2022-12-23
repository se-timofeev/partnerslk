package ru.planetnails.partnerslk.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.price.dto.PriceAddDto;
import ru.planetnails.partnerslk.service.PriceService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "/api/v1/prices")
public class PriceRestControllerV1 {
    PriceService priceService;

    @PostMapping
    @PutMapping
    public String add(@RequestBody List<PriceAddDto> prices) {
        priceService.add(prices);
        return "Your data has been queued.";
    }
}
