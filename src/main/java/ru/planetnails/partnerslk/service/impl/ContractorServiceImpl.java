package ru.planetnails.partnerslk.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorAddDto;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorMapper;
import ru.planetnails.partnerslk.model.contractor.dto.ContractorOutDto;
import ru.planetnails.partnerslk.repository.ContractorRepository;
import ru.planetnails.partnerslk.service.ContractorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ContractorServiceImpl implements ContractorService {
    private ContractorRepository contractorRepository;

    @Override
    @Async
    public void add(List<ContractorAddDto> contractorsList) {
        log.info("Add contractors as List of elements ");
        List<Contractor> contractors = contractorsList.stream()
                .map(y -> ContractorMapper.fromContractorAddDtoToContractor(y)).collect(Collectors.toList());
        try {
            contractorRepository.saveAll(contractors);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        contractorRepository.deleteById(id);
    }

    @Override
    public Contractor findById(String id) {
        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrator_id not found"));
        return contractor;
    }

    @Override
    public List<ContractorOutDto> findContractorsByPartnerId(String partnerId) {
        List<ContractorOutDto> contractors = contractorRepository.findContractorsByPartnerId(partnerId)
                .stream().map(ContractorMapper::fromContractorToContractorOutDto)
                .collect(Collectors.toList());
        return contractors;
    }

    @Override
    public List<Contractor> findAllContractors() {
        return contractorRepository.findAllContractors();
    }
}
