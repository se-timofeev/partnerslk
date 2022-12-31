package ru.planetnails.partnerslk.model.contractor.dto;

import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.contractor.StatusContractor;

import java.time.LocalDateTime;

public class ContractorMapper {
    public static ContractorOutDto fromContractorToContractorOutDto(Contractor contractor) {
        return ContractorOutDto.builder()
                .id(contractor.getId())
                .name(contractor.getName())
                .description(contractor.getDescription())
                .inn(contractor.getInn())
                .kpp(contractor.getKpp())
                .legalAddress(contractor.getLegalAddress())
                .actualAddress(contractor.getActualAddress())
                .status(contractor.getStatus()).build();


    }

    public static Contractor fromContractorAddDtoToContractor(ContractorAddDto contractorAddDto) {
        return Contractor.builder()
                .id(contractorAddDto.getId())
                .name(contractorAddDto.getName())
                .description(contractorAddDto.getDescription())
                .inn(contractorAddDto.getInn())
                .kpp(contractorAddDto.getKpp())
                .legalAddress(contractorAddDto.getLegalAddress())
                .actualAddress(contractorAddDto.getActualAddress())
                .status(contractorAddDto.getStatus() == null ?
                        StatusContractor.BLOCKED :
                        contractorAddDto.getStatus())
                .updated(LocalDateTime.now()).build();
    }
}
