package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.order.dto.OrderAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;
import ru.planetnails.partnerslk.service.OrderService;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Orders", description = "You can create, modify and, get the orders")
@RequestMapping(value = "/api/v1/orders")
public class OrderRestControllerV1 {

    private final OrderService orderService;

    @Operation(summary = "Create or update the order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order has been registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderOutDto.class))})
    })
    @PostMapping
    @PutMapping
    public String add(@RequestBody OrderAddDto orderAddDto) {
        log.info(String.format("Получен эндпоинт POST /api/v1/order"));
        return String.format("Заказ успешно создан; orderId = %s", orderService.add(orderAddDto));
    }
}
