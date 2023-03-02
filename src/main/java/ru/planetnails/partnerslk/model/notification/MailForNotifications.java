package ru.planetnails.partnerslk.model.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.planetnails.partnerslk.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "mails_for_notifications")
@NoArgsConstructor
@Getter
@Setter
public class MailForNotifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String email;

    public MailForNotifications(User user, String email) {
        this.user = user;
        this.email = email;
    }
}
