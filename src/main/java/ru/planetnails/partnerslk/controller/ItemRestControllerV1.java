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
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.item.ItemQueryParams;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.GroupDtoOut;
import ru.planetnails.partnerslk.service.ItemService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping(value = "/api/v1/items")
@Tag(name = "Items", description = "Вы можете создать, обновить, получить данные о товарах")
public class ItemRestControllerV1 {
    private ItemService itemService;

    @PostMapping()
    @PutMapping
    public String add(@RequestBody List<ItemAddDto> items) {
        itemService.add(items);
        return "Your data has been queued.";
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ItemDtoOut> getItemsByGroupId(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @RequestParam(name = "size", defaultValue = "10") Integer size,
                                              @RequestParam String partnerId,
                                              @RequestParam String groupId) {
        log.info(String.format("Получен эндпоинт GET /api/v1/items/; partnerId = %s, groupId = %s", partnerId, groupId));
        return itemService.getItemsByGroupId(groupId, from, size, partnerId);
    }


    @Operation(summary = "Получить список групп отфильтрованных по полям \"level\" и  \"groupId\"; " +
            "Поля \"level\" и  \"groupId\" опционально.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает список групп. В случае отсутствия групп," +
                    " удовлетворяющих параметром, возвращает пустой список",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GroupDtoOut.class)))}),
    })
    @GetMapping("/groups")
    public List<GroupDtoOut> getFilteredGroups(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size,
                                               @RequestParam(required = false) Integer level,
                                               @RequestParam(required = false) String groupId) {
        log.info(String.format("Получен эндпоинт GET /api/v1/items/groups; level = %d, parentId = %s", level, groupId));
        return itemService.getFilteredGroups(level, groupId, from, size);
    }

    @Operation(summary = "Получить список товаров отфильтрованных по полям \"name\", \"description\", " +
            "\"countryOfOrigin\", \"vendorCode\", \"minPrice\", \"maxPrice\". Все поля опционално ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает список товаров. В случае отсутствия товаров," +
                    " удовлетворяющих параметром, возвращает пустой список",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemDtoOut.class)))}),
    })
    @GetMapping("/param/{partnerId}")
    public List<ItemDtoOut> getItemByParams(@PathVariable String partnerId,
                                            @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) String description,
                                            @RequestParam(required = false) List<String> countries,
                                            @RequestParam(required = false) String vendorCode,
                                            @RequestParam(required = false) Double minPrice,
                                            @RequestParam(required = false) Double maxPrice) {
        log.info(String.format("Получен эндпоинт GET /api/v1/items/param; partnerId = %s, name = %s, " +
                        "description = %s, countries = %s, vendorCode = %s, minPrice = %f, maxPrice = %f",
                partnerId, name, description, countries, vendorCode, minPrice, maxPrice));
        ItemQueryParams params = ItemQueryParams.builder()
                .name(name)
                .description(description)
                .countries(countries)
                .vendorCode(vendorCode)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        return itemService.getItemByParams(partnerId, params, from, size);
    }
}
