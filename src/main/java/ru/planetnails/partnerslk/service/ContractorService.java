package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.contractor.dto.ContractorAddDto;

import java.util.List;

public interface ContractorService {

    void add(List<ContractorAddDto> contractors);

}
