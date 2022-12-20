package ru.planetnails.partnerslk.model.baseClasses;

import lombok.*;
import ru.planetnails.partnerslk.model.user.User;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

}
