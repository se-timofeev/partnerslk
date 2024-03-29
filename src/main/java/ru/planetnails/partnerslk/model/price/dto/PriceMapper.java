package ru.planetnails.partnerslk.model.price.dto;

import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.price.Price;

import java.time.LocalDateTime;

public class PriceMapper {
    public static Price fromPriceAddDtoToPrice(PriceAddDto priceAddDto) {
        return Price.builder()
                .id(priceAddDto.getId())
                .retail(priceAddDto.getRetail())
                .sale(priceAddDto.getSale())
                .item(new Item(priceAddDto.getId()))
                .updated(LocalDateTime.now())
                .build();
    }
}
