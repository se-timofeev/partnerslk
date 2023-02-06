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

    public static PartnerOutDto fromPartnerToPartnerOutDto(Partner partner) {
        return PartnerOutDto.builder()
                .id(partner.getId())
                .name(partner.getName())
                .discount(partner.getDiscount())
                .account(partner.getAccount()).build();
    }
}
