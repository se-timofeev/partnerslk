package ru.planetnails.partnerslk.model.item.dto;

import ru.planetnails.partnerslk.model.group.Group;
import ru.planetnails.partnerslk.model.item.Item;

import java.time.LocalDateTime;

public class ItemMapper {
    public static Item fromItemAddDtoToItem(ItemAddDto itemAddDto) {
        Item item = new Item();
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
        item.setIsNovelty(itemAddDto.getIsNovelty());
        return item;
    }

    public static Group fromItemAddDtoToGroup(ItemAddDto itemAddDto) {
        Group group = new Group();
        group.setId(itemAddDto.getId());
        group.setName(itemAddDto.getName());
        group.setParentId(itemAddDto.getParent_id());
        group.setLevel(itemAddDto.getLevel());
        return group;
    }

    public static ItemDtoOut toItemDtoOut(Item item, Integer discount) {
        ItemDtoOut itemDtoOut = new ItemDtoOut();
        itemDtoOut.setName(item.getName());
        itemDtoOut.setDescription(item.getDescription());
        itemDtoOut.setIsGroup(item.getIsGroup());
        itemDtoOut.setId(item.getId());
        itemDtoOut.setLevel(item.getLevel());
        itemDtoOut.setVendorCode(item.getVendorCode());
        itemDtoOut.setParent_id(item.getParentId());
        itemDtoOut.setIsOutOfStock(item.getIsOutOfStock());
        itemDtoOut.setCountryOfOrigin(item.getCountryOfOrigin());
        if (item.getPrice() != null) {
            itemDtoOut.setDiscount(discount);
            itemDtoOut.setSalePriceBeforeDiscount(item.getPrice().getSale());
            itemDtoOut.setRetailPriceBeforeDiscount(item.getPrice().getRetail());
            itemDtoOut.setSalePrice(item.getPrice().getSale() * (100 - discount) / 100);
            itemDtoOut.setRetailPrice(item.getPrice().getRetail() * (100 - discount) / 100);
        }
        itemDtoOut.setIsNovelty(item.getIsNovelty());
        return itemDtoOut;
    }

    public static ItemAddDto toItemAddDto(Item item) {
        return ItemAddDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .vendorCode(item.getVendorCode())
                .id(item.getId()).build();
    }

    public static ItemDtoOutGroups toItemDtoOutShort(Item item) {
        return new ItemDtoOutGroups(item.getId(), item.getName(), item.getParentId(), item.getLevel());
    }
}
