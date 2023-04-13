package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorAddDto;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorOutDto;

import java.util.List;

public interface ContractorService {

    void add(List<ContractorAddDto> contractors);

    Contractor findById(String id)
            ;

    List<ContractorOutDto> findContractorsByPartnerId(String partnerId);
}
