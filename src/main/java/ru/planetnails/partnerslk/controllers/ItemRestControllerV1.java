package ru.planetnails.partnerslk.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.item.Dto.*;
import ru.planetnails.partnerslk.service.ItemService;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping (value = "/api/v1/items")
public class ItemRestControllerV1 {
private ItemService itemService;

    @PostMapping()
    public String add(@RequestBody List<ItemAddDto> items){
         itemService.add(items);
        return "Your data has been queued.";
    }
    @GetMapping(produces =  "application/json;charset=UTF-8")
    public List<ItemDtoOut> getAll(){
       return itemService.getAll();
    }
}
