package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.price.Price;

public interface PriceRepository extends JpaRepository<Price,String> {
}
