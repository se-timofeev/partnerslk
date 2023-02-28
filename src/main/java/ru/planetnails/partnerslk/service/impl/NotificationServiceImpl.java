package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.notification.Notification;
import ru.planetnails.partnerslk.model.notification.dto.NotificationDtoOut;
import ru.planetnails.partnerslk.model.notification.dto.NotificationMapper;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.repository.NotificationRepository;
import ru.planetnails.partnerslk.service.NotificationService;
import ru.planetnails.partnerslk.service.UserService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private UserService userService;

    @Override
    public Page<NotificationDtoOut> getNotificationsByUserId(String userId, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size,
                Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Notification> notifications = notificationRepository.findAllByUserId(userId, pageRequest);
        if (notifications.isEmpty()) throw new NotFoundException("У вас пока что нет ни одного уведомления");
        return notifications.map(NotificationMapper::toNotificationDtoOut);
    }

    @Override
    public void addNotification(Order order, String userId) {
        User user = userService.findById(userId);
        notificationRepository.save(createNotification(order, user));
    }

    private Notification createNotification(Order order, User user) {
        return Notification.builder()
                .user(user)
                .order(order)
                .isRead(false)
                .createTime(LocalDateTime.now())
                .status(order.getStatus())
                .build();
    }
}
