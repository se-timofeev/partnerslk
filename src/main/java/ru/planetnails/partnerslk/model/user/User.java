package ru.planetnails.partnerslk.model.user;

import lombok.Data;
import lombok.ToString;
import ru.planetnails.partnerslk.model.baseClasses.BaseEntity;
import ru.planetnails.partnerslk.model.baseClasses.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
@Data

public class User extends BaseEntity {
    @Column (name = "first_name")
    private String firstName;
    @Column (name = "last_name")
    private String lastName;
    @Column (name = "email")
    private String email;
    @Column (name = "password")
    private String password;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name="user_roles",
    joinColumns={@JoinColumn(name="user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
