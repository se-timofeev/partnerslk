package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.contractor.Contractor;

import java.util.List;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, String> {
    List<Contractor> findContractorsByPartnerId(String partnerId);
}

