package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOutGroups;
import ru.planetnails.partnerslk.service.ItemService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping (value = "/api/v1/items")
@Tag(name = "Items", description = "Вы можете создать, обновить, получить данные о товарах")
public class ItemRestControllerV1 {
private ItemService itemService;

    @PostMapping()
    @PutMapping
    public String add(@RequestBody List<ItemAddDto> items){
         itemService.add(items);
        return "Your data has been queued.";
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ItemDtoOut> getFilteredItems(@RequestParam(required = false, name = "group_id") String groupId,
                                             @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get all requests from={}, size={}", from, size);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        return itemService.getFilteredItems(groupId, pageRequest);
    }


    @Operation(summary = "Получить список товаров отфильтрованных по полям \"level\" и  \"parentId\"; " +
            "Поля \"level\" и  \"parentId\" опционально.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает список товаров. В случае отсутствия товаров," +
                    " удовлетворяющих параметром, возвращает пустой список",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemDtoOutGroups.class)))}),
    })
    @GetMapping("/groups")
    public List<ItemDtoOutGroups> getFilteredGroupItems(@RequestParam(required = false) Integer level,
                                                        @RequestParam(required = false) String parentId) {
        log.info(String.format("Получен эндпоинт GET /api/v1/items/groups; level = %d, parentId = %s", level, parentId));
        return itemService.getFilteredGroups(level, parentId);
    }
}
