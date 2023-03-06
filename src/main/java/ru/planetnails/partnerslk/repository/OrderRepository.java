package ru.planetnails.partnerslk.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.partner.Partner;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByPartner(Partner partner, PageRequest pageRequest);

}
