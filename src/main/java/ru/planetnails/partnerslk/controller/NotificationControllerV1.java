package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.notification.dto.EmailDtoOut;
import ru.planetnails.partnerslk.model.notification.dto.NotificationDtoOut;
import ru.planetnails.partnerslk.service.NotificationService;

import javax.validation.constraints.Email;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api/v1/notifications")
@Tag(name = "Notifications", description = "Вы можете получать список уведомлений , а так же настраивать их рассылку" +
        "на электронные адреса")
public class NotificationControllerV1 {

    private NotificationService notificationService;

    @GetMapping("/{userId}")
    public Page<NotificationDtoOut> getNotificationsByUserId(@PathVariable String userId,
                                                             @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен эндпоинт GET /api/v1/notifications/{userId}, userId = " + userId);
        return notificationService.getNotificationsByUserId(userId, from, size);
    }

    @PostMapping("/email")
    public EmailDtoOut addEmail(@RequestParam @Email String email, @RequestParam String userId) {
        log.info("Получен эндпоинт POST /api/v1/notifications/email, email = {}, userId = {}", email, userId);
        return notificationService.addEmail(email, userId);
    }

    @GetMapping("/email/{userId}")
    public List<EmailDtoOut> getEmailsByUserId(@PathVariable String userId) {
        log.info("Получен эндпоинт GET /api/v1/notifications/email, userId = {}", userId);
        return notificationService.getEmailsByUserId(userId);
    }

    @GetMapping("/email/{mailId}")
    public EmailDtoOut getEmailById(@PathVariable Integer mailId) {
        log.info("Получен эндпоинт GET /api/v1/notifications/email, mailId = {}", mailId);
        return notificationService.getEmailById(mailId);
    }

    @DeleteMapping
    public void deleteEmailById(@RequestParam Integer mailId) {
        log.info("Получен эндпоинт DELETE /api/v1/notifications/email, mailId = {}", mailId);
        notificationService.deleteEmailById(mailId);
    }
}
