package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.model.price.dto.PriceMapper;

import ru.planetnails.partnerslk.model.price.Price;
import ru.planetnails.partnerslk.model.price.dto.PriceAddDto;
import ru.planetnails.partnerslk.repository.PriceRepository;
import ru.planetnails.partnerslk.service.PriceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PriceServiceImpl implements PriceService {
    PriceRepository priceRepository;
    @Override
    @Async
    public void add(List<PriceAddDto> priceAddDto) {
        log.info("Add prices as List ");
        List<Price> prices =priceAddDto.stream().map(x-> PriceMapper.fromPriceAddDtoToPrice(x)).collect(Collectors.toList());
        try {
            priceRepository.saveAll(prices);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
