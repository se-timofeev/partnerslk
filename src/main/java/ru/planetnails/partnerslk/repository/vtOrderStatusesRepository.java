package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;

public interface vtOrderStatusesRepository extends JpaRepository<vtOrderStatuses, Long> {
}
