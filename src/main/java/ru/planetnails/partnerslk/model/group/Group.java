package ru.planetnails.partnerslk.model.group;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "level")
    private Integer level;
}
