package ru.planetnails.partnerslk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.image.Image;

public interface ImageRepository extends JpaRepository<Image, String> {

    Page<Image> findByItemId(String itemId, Pageable pageable);
}
