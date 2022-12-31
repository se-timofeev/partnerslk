package ru.planetnails.partnerslk.model.contractor;

import lombok.*;
import ru.planetnails.partnerslk.model.partner.Partner;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratcotrs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Contractor {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "inn")
    private String inn;

    @Column(name = "kpp")
    private String kpp;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "actual_address")
    private String actualAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(name="updated")
    private LocalDateTime updated;

    @Column(name="status")
    private StatusContractor statusContractor ;


}
