package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.image.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {

    List<Image> findByItemId(String itemId);
}
