package ru.planetnails.partnerslk.model.group;

import lombok.*;

import jakarta.persistence.*;


@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "level")
    private Integer level;
}
