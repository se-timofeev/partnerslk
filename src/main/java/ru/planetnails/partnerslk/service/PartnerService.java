package ru.planetnails.partnerslk.service;

import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.model.partner.dto.PartnerAddDto;

public interface PartnerService {
    void add (PartnerAddDto partnerAddDto);

    Partner findPartnerById(String partnerId);
}
