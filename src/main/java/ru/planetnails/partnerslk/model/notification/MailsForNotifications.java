package ru.planetnails.partnerslk.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.planetnails.partnerslk.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "mails_for_notifications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MailsForNotifications {

    @Id
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;
    private String email;
}
