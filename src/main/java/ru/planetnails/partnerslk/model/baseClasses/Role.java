package ru.planetnails.partnerslk.model.baseClasses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.planetnails.partnerslk.model.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table (name="roles")
public class Role extends BaseEntity {
    @ManyToMany (mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> users;

}
