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
        item.setId(itemAddDto.getId());
        item.setLevel(itemAddDto.getLevel());
        item.setVendorCode(itemAddDto.getVendorCode());
        item.setGroupId(itemAddDto.getParentId());
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
        group.setGroupId(itemAddDto.getParentId());
        group.setLevel(itemAddDto.getLevel());
        return group;
    }

    public static ItemDtoOut toItemDtoOut(Item item, Integer discount) {
        ItemDtoOut itemDtoOut = new ItemDtoOut();
        itemDtoOut.setName(item.getName());
        itemDtoOut.setDescription(item.getDescription());
        itemDtoOut.setId(item.getId());
        itemDtoOut.setLevel(item.getLevel());
        itemDtoOut.setVendorCode(item.getVendorCode());
        itemDtoOut.setGroupId(item.getGroupId());
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

    public static GroupDtoOut toGroupDtoOut(Group group) {
        return new GroupDtoOut(group.getId(), group.getName(), group.getGroupId(), group.getLevel());
    }
}
