package ru.planetnails.partnerslk.model.user;

import lombok.*;
import ru.planetnails.partnerslk.model.baseClasses.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @Id
    private String id;
    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "email")
    private String email;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

//    @Override
//    public String toString() {
//        return "User{" +
//                "firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", roles=" + roles +
//                '}';
//    }
}
