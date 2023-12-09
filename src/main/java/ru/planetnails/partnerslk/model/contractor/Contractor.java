package ru.planetnails.partnerslk.model.contractor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contractors")
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

    @JoinColumn(name = "partner_id")
    @NotBlank
    private String partnerId;
    @JoinColumn(name = "partner_name")
    private String partnerName;

    @Column(name = "updated")
    private LocalDateTime updated;


}
