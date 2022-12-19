package ru.planetnails.partnerslk.model.partner;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="partners")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Partner {
    @Id
    private String id;

    @Column (name="name")
    private  String name;

    @Column (name="discount")
    private int discount;

    @Column(name ="account")
    private String account;
}
