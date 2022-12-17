package ru.planetnails.partnerslk.model.item.Dto;

import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.item.StatusItem;

import java.time.LocalDateTime;

public class ItemMapper {
    public static Item fromItemAddDtoToItem(ItemAddDto itemAddDto) {
         Item item=new Item ();
         item.setName(itemAddDto.getName());
                item.setDescription(itemAddDto.getDescription());
                item.setDescriptionHtml(itemAddDto.getDescriptionHtml());
                item.setIsGroup(itemAddDto.getIsGroup());
                item.setId(itemAddDto.getId());
                item.setLevel(itemAddDto.getLevel());
                item.setVendorCode(itemAddDto.getVendorCode());
                item.setParentId(itemAddDto.getParent_id());
                item.setIsOutOfStock(itemAddDto.getIsOutOfStock());
                item.setUpdated(LocalDateTime.now());
                item.setCountryOfOrigin(itemAddDto.getCountryOfOrigin());

        return item;
    }
    public static ItemDtoOut toDtoOut(Item item){
        ItemDtoOut itemDtoOut=new ItemDtoOut ();
        itemDtoOut.setName(item.getName());
        itemDtoOut.setDescription(item.getDescription());
        itemDtoOut.setIsGroup(item.getIsGroup());
        itemDtoOut.setId(item.getId());
        itemDtoOut.setLevel(item.getLevel());
        itemDtoOut.setVendorCode(item.getVendorCode());
        itemDtoOut.setParent_id(item.getParentId());
        itemDtoOut.setIsOutOfStock(item.getIsOutOfStock());
        itemDtoOut.setCountryOfOrigin(item.getCountryOfOrigin());

        return itemDtoOut;
    }

    public static ItemAddDto toItemAddDto(Item item) {
        return  ItemAddDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .vendorCode(item.getVendorCode())
                .id(item.getId()).build();

    }
}
