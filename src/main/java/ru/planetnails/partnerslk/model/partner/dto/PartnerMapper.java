package ru.planetnails.partnerslk.model.partner.dto;

import ru.planetnails.partnerslk.model.partner.Partner;

public class PartnerMapper {
    public static Partner fromPartnerAddDtoToPartner(PartnerAddDto partnerAddDto) {
        return Partner.builder()
                .id(partnerAddDto.getId())
                .name(partnerAddDto.getName())
                .discount(partnerAddDto.getDiscount())
                .account(partnerAddDto.getAccount()).build();


    }
}
