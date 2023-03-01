package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.exception.BadRequestException;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.notification.MailForNotifications;
import ru.planetnails.partnerslk.model.notification.Notification;
import ru.planetnails.partnerslk.model.notification.dto.EmailDtoOut;
import ru.planetnails.partnerslk.model.notification.dto.NotificationDtoOut;
import ru.planetnails.partnerslk.model.notification.dto.NotificationMapper;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.repository.MailForNotificationsRepository;
import ru.planetnails.partnerslk.repository.NotificationRepository;
import ru.planetnails.partnerslk.service.NotificationService;
import ru.planetnails.partnerslk.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private UserService userService;
    private MailForNotificationsRepository mailForNotificationsRepository;

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

    @Override
    public EmailDtoOut addEmail(String email, String userId) {
        User user = userService.findById(userId);
        if (mailForNotificationsRepository.findByEmailAndUserId(email, userId) != null) throw new BadRequestException(
                String.format("У пользователя с id = %s адрес для уведомлений %s уже указан", userId, email));
        MailForNotifications mail = mailForNotificationsRepository.save(new MailForNotifications(user, email));
        return NotificationMapper.toEmailDtoOut(mail);
    }

    @Override
    public List<EmailDtoOut> getEmailsByUserId(String userId) {
        List<MailForNotifications> mails = mailForNotificationsRepository.findAllByUserId(userId);
        return mails.stream().map(NotificationMapper::toEmailDtoOut).collect(Collectors.toList());
    }

    @Override
    public EmailDtoOut getEmailById(Integer mailId) {
        MailForNotifications mail = mailForNotificationsRepository.findById(mailId).orElseThrow(() ->
                new NotFoundException(String.format("Запись об электронном адресе с id = %d не обнаружено", mailId)));
        return NotificationMapper.toEmailDtoOut(mail);
    }

    @Override
    public void deleteEmailById(Integer mailId) {
        if (!mailForNotificationsRepository.existsById(mailId))
            throw new NotFoundException(String.format("Запись об электронном адресе с id = %d не обнаружено", mailId));
        mailForNotificationsRepository.deleteById(mailId);
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
