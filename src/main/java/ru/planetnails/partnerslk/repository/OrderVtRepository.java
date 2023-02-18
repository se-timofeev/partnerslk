package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.order.OrderVt;

public interface OrderVtRepository extends JpaRepository<OrderVt, Long> {
}
