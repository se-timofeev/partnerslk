package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.partner.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, String> {
}
