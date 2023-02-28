package ru.planetnails.partnerslk.service;

import org.springframework.data.domain.Page;
import ru.planetnails.partnerslk.model.notification.dto.NotificationDtoOut;
import ru.planetnails.partnerslk.model.order.Order;


public interface NotificationService {
    Page<NotificationDtoOut> getNotificationsByUserId(String userId, Integer from, Integer size);

    void addNotification(Order order, String userId);
}
