package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.notification.MailForNotifications;

import java.util.List;

public interface MailForNotificationsRepository extends JpaRepository<MailForNotifications, Integer> {

    List<MailForNotifications> findAllByUserId(String userId);
}
