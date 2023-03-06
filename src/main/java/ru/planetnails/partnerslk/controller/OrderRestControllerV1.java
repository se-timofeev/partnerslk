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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.dto.OrderAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderMapper;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;
import ru.planetnails.partnerslk.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
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
                            schema = @Schema(implementation = OrderAddDto.class))})
    })
    @PostMapping
    @PutMapping
    public String add(@RequestBody @Validated OrderAddDto orderAddDto) {
        log.info("Получен эндпоинт POST /api/v1/orders");
        return String.format("Заказ успешно создан; orderId = %s", orderService.add(orderAddDto));
    }

    @Operation(summary = "Get the order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<OrderOutDto> getOrderById(@RequestParam String orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        OrderOutDto result = OrderMapper.fromOrderToOrderOutDto(order);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Получить список заказов партнера по \"partnerId\".")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает список заказов. В случае отсутствия заказов," +
                    " возвращает пустой список",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderOutDto.class)))}),
    })
    @GetMapping("/partner")
    public ResponseEntity<List<OrderOutDto>> getPartnersOrders(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                                               @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                               @RequestParam String partnerId) {
        log.info("Get all orders for Partner with from={}, size={}, partnerId={}", from, size, partnerId);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        List<Order> orders = orderService.findAllByPartnerId(partnerId, pageRequest);
        if (orders == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<OrderOutDto> result = orders.stream()
                .map(OrderMapper::fromOrderToOrderOutDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Set a new status for the order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    @PatchMapping("/status")
    public OrderOutDto setStatusForOrder(@RequestParam String orderId,
                                         @RequestParam String status,
                                         @RequestParam String userId) {
        return orderService.setStatusForOrder(orderId, status, userId);
    }
}
