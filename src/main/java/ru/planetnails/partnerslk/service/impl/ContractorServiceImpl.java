package ru.planetnails.partnerslk.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.contractor.StatusContractor;
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
    public Contractor findById(String id) {
        return null;
    }

    @Override
    @Transactional
    @Async
    public void add(List<ContractorAddDto> contractorsList) {
        log.info("Add contractors as List of elements ");
        List<Contractor> contractors = contractorsList.stream().map(
                x -> ContractorMapper.fromContractorAddDtoToContractor(x)).collect(Collectors.toList());
        try {
            contractorRepository.saveAll(contractors);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    @Override
    @Transactional
    public ContractorOutDto setActive(String id) {
        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Контрагент не найден!"));
        contractor.setStatus(StatusContractor.ACTIVE);
        contractorRepository.save(contractor);
        return ContractorMapper.fromContractorToContractorOutDto(contractor);
    }

    @Override
    @Transactional
    public ContractorOutDto setBlocked(String id) {
        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Контрагент не найден!"));
        contractor.setStatus(StatusContractor.BLOCKED);
        contractorRepository.save(contractor);
        return ContractorMapper.fromContractorToContractorOutDto(contractor);
    }

}
