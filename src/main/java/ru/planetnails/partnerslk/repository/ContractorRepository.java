package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.contractor.Contractor;

import java.util.List;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, String> {
    List<Contractor> findContractorsByPartnerId(String partnerId);

    @Query(
            value = "" +
                    "SELECT " +
                    "t1.id,  " +
                    "t1.name,  " +
                    "t1.description,  " +
                    "t1.inn, " +
                    "t1.kpp, " +
                    "isnull(t1.legal_address,'') as legal_address, " +
                    "isnull(t1.actual_address,'') as actual_address, " +
                    "t1.updated, " +
                    "t2.id as partner_id, " +
                    "isnull(t2.name,'-') as partnername " +
                    "FROM " +
                    "contractors as t1 " +
                    "left join partners as t2 on t1.partner_id=t2.id " +
                    "",
            nativeQuery = true)
    List<Contractor> findAllContractors();
}

