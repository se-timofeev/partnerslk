package ru.planetnails.partnerslk.broker;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "RabbitMQ", description = "Сервис проверки работоспособности RabbitMQ")
@RequestMapping(value = "/api/v1/rabbit")
public class RabbitController {

    private final RabbitMQProducerService rabbitMQProducerService;

    @Autowired
    public RabbitController(RabbitMQProducerService rabbitMQProducerService) {
        this.rabbitMQProducerService = rabbitMQProducerService;
    }

    @Operation(summary = "Проверка статуса RabbitMQ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rabbit MQ и доступ к нему работает",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))}),

    })
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.info(String.format(" GET /rabbit/health"));
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/send")
    public void sendMessageToRabbit(@RequestBody String s) {
        rabbitMQProducerService.sendMessage(s);
    }
}

