package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorAddDto;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorOutDto;

import java.util.List;

public interface ContractorService {
    Contractor findById(String id);

    void add(List<ContractorAddDto> contractors);

    ContractorOutDto setActive(String id);

    ContractorOutDto setBlocked(String id);
}
