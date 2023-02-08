package ru.planetnails.partnerslk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.partner.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, String> {

    @Query(
            value = "" +
                    "SELECT " +
                    "* " +
                    "FROM " +
                    "partners as p " +
                    "inner join user_partners as up on p.id=up.partner_id " +
                    "inner join users as u on up.user_id=u.id " +
                    "WHERE u.name = ?1",
            nativeQuery = true)
    Partner findPartnerByUsername(String username);

    @Query(
            value = "" +
                    "SELECT " +
                    "* " +
                    "FROM " +
                    "partners as p " +
                    "inner join user_partners as up on p.id=up.partner_id " +
                    "inner join users as u on up.user_id=u.id " +
                    "WHERE u.id = ?1",
            nativeQuery = true)
    Partner findPartnerByUserId(String id);

}
