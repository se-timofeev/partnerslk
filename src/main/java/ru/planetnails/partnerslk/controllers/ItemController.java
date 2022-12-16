package ru.planetnails.partnerslk.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.planetnails.partnerslk.model.item.Dto.ItemAddDto;
import ru.planetnails.partnerslk.service.ItemService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping (value = "/api/v1/items")
public class ItemController {
private ItemService itemService;
    @PostMapping
    public ItemAddDto add(@Valid @RequestBody ItemAddDto itemDto){
        return itemService.add(itemDto);
    }
}
