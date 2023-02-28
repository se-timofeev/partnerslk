package ru.planetnails.partnerslk.model.notification.dto;

import ru.planetnails.partnerslk.model.notification.Notification;

public class NotificationMapper {

    public static NotificationDtoOut toNotificationDtoOut(Notification notification) {
        return NotificationDtoOut.builder()
                .id(notification.getId())
                .orderNum(notification.getOrder().getNum())
                .orderDate(notification.getOrder().getOrderDate())
                .sum(notification.getOrder().getSumWithDiscount())
                .status(notification.getStatus().toString())
                .statusUpdateTime(notification.getCreateTime())
                .isRead(notification.isRead())
                .build();
    }
}
