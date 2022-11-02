package ru.planetnails.partnerslk.model.baseClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * entity name
     */
    private String name;

    /**
     * entity full description
     */
    private String description;

    /**
     * object's creation date
     */
    @CreatedDate
    @Column(name = "created")
    private Date created;

    /**
     * object's update date
     */
    @LastModifiedDate
    @Column(name = "updated")
    private Date updated;

    /**
     * objects status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
