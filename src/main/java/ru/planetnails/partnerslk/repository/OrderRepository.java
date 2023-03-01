package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
