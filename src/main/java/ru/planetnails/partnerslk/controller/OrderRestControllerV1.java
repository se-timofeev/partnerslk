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
import ru.planetnails.partnerslk.model.order.dto.*;
import ru.planetnails.partnerslk.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@CrossOrigin
@AllArgsConstructor
@Tag(name = "Orders", description = "You can create, modify and, get the orders")
@RequestMapping(value = "/api/v1/orders")
public class OrderRestControllerV1 {

    private final OrderService orderService;

    @Operation(summary = "Create the order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order has been registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderAddDto.class))})
    })
    @PostMapping
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
        return orderService.statusForOrderUser(orderId, status, userId);
    }

    @Operation(summary = "Get the status for order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the status for order",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    @GetMapping("/status/condition")
    public ResponseEntity<String> getStatusForOrder(@RequestParam String orderId) {
        log.info("Get status for order with orderId={}", orderId);
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Status for order with orderId={}, is status={}", orderId, order.getStatus().toString());
        return new ResponseEntity<>(order.getStatus().toString(), HttpStatus.OK);
    }

    @Operation(summary = "Get the header of order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderFirstPartOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "The first part of order not found",
                    content = @Content)
    })
    @GetMapping("/header")
    public ResponseEntity<OrderFirstPartOutDto> getHeaderOfOrder(@RequestParam String orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        OrderFirstPartOutDto result = OrderMapper.fromOrderToOrderFirstPartOutDto(order);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get the table of items by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the second part of order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderSecondPartOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "The second part of order not found",
                    content = @Content)
    })
    @GetMapping("/table")
    public ResponseEntity<OrderSecondPartOutDto> getTableOfOrder(@RequestParam String orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        OrderSecondPartOutDto result = OrderMapper.fromOrderSecondPartOutDto(order);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Update the order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order has been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderAddDto.class))})
    })
    @PutMapping("/update")
    public String update(@RequestBody @Validated OrderAddDto orderAddDto,
                         @RequestParam String orderId) {
        log.info("Received endpoint POST /api/v1/orders/update");
        return String.format("Order updated successfully; orderId = %s", orderService.update(orderAddDto, orderId));
    }
}
