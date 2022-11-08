package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.logger.Logger;

@Repository
public interface LoggingRepository extends JpaRepository <Logger,Long> {
}
