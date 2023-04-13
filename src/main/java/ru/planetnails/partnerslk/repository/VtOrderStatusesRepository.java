package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.planetnails.partnerslk.model.order.VtOrderStatuses;

public interface VtOrderStatusesRepository extends JpaRepository<VtOrderStatuses, Long> {
}
