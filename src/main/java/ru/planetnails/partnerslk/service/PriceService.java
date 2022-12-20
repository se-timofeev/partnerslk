package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.price.dto.PriceAddDto;

import java.util.List;

public interface PriceService {
    void add(List<PriceAddDto> priceAddDto);
}
