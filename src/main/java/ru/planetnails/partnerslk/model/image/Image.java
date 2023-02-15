package ru.planetnails.partnerslk.model.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "images")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class Image {

    @Id
    private String id;
    @Column(name = "base64")
    private String base64;
    @Column(name = "item_id")
    private String itemId;
}
