package ru.planetnails.partnerslk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.notification.Notification;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Page<Notification> findAllByUserId(String userId, Pageable pageable);
}
