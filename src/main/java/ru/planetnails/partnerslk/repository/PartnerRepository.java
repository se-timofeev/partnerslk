package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.partner.Partner;

import java.util.Optional;


@Repository
public interface PartnerRepository extends JpaRepository<Partner, String> {
    Optional<Partner> findById(String id);
}
