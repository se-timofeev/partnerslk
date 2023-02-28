package ru.planetnails.partnerslk.model.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.planetnails.partnerslk.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "mails_for_notifications")
@NoArgsConstructor
@Getter
public class MailForNotifications {

    @Id
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;
    private String email;

    public MailForNotifications(User user, String email) {
        this.user = user;
        this.email = email;
    }
}
