package ru.planetnails.partnerslk.model.partner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "partners")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@OnDelete(action = OnDeleteAction.CASCADE)
public class Partner {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "discount")
    private int discount;

    @Column(name = "account")
    private String account;
}
