package ru.planetnails.partnerslk.service;

import org.springframework.data.domain.Page;
import ru.planetnails.partnerslk.model.notification.dto.EmailDtoOut;
import ru.planetnails.partnerslk.model.notification.dto.NotificationDtoOut;
import ru.planetnails.partnerslk.model.order.Order;

import java.util.List;


public interface NotificationService {
    Page<NotificationDtoOut> getNotificationsByUserId(String userId, Integer from, Integer size);

    void addNotification(Order order, String userId);

    EmailDtoOut addEmail(String email, String userId);

    List<EmailDtoOut> getEmailsByUserId(String userId);

    EmailDtoOut getEmailById(Integer mailId);

    void deleteEmailById(Integer mailId);
}
