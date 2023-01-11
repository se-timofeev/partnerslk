package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.model.partner.dto.PartnerAddDto;
import ru.planetnails.partnerslk.model.partner.dto.PartnerMapper;
import ru.planetnails.partnerslk.repository.PartnerRepository;
import ru.planetnails.partnerslk.service.PartnerService;

@Service
@Slf4j
@AllArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    PartnerRepository partnerRepository;

    @Override
    @Transactional
    @Async
    public void add(PartnerAddDto partnerAddDto) {
        log.info("Add partner ");
        try {
            partnerRepository.save(PartnerMapper.fromPartnerAddDtoToPartner(partnerAddDto));

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public Partner findPartnerById(String partnerId) {
        return partnerRepository.findById(partnerId).orElseThrow(() -> new NotFoundException("partner not found"));
    }
}
