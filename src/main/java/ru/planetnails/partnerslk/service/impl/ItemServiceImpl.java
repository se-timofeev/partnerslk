package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.item.ItemQueryParams;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOutGroups;
import ru.planetnails.partnerslk.model.item.dto.ItemMapper;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.repository.itemRepository.ItemRepository;
import ru.planetnails.partnerslk.service.ItemService;
import ru.planetnails.partnerslk.service.PartnerService;
import ru.planetnails.partnerslk.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor

public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    private PartnerService partnerService;
    private UserService userService;

    @Override
    public ItemAddDto add(ItemAddDto itemAddDto) {
        log.info("Add item {}", itemAddDto);

        Item item = ItemMapper.fromItemAddDtoToItem(itemAddDto);

        try {
            itemRepository.save(item);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return ItemMapper.toItemAddDto(item);
    }

    @Override
    @Transactional
    @Async
    public void add(List<ItemAddDto> itemsAddDto) {
        log.info("Add items as List ");
        List<Item> items = itemsAddDto.stream().map(x -> ItemMapper.fromItemAddDtoToItem(x)).collect(Collectors.toList());
        try {
            itemRepository.saveAll(items);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    @Override
    public List<ItemDtoOut> getFilteredItems(String parentId, Integer from, Integer size, String partnerId) {
        Partner partner = partnerService.findPartnerById(partnerId);
        List<Item> items = itemRepository.getFilteredItems(parentId, from, size);
        return items.stream().map(x -> ItemMapper.toItemDtoOut(x, partner.getDiscount())).collect(Collectors.toList());
    }

    @Override
    public List<ItemDtoOutGroups> getFilteredGroups(Integer level, String parentId, Integer from, Integer size) {
        List<Item> items = itemRepository.getFilteredGroups(level, parentId, from, size);
        return items.stream().map(ItemMapper::toItemDtoOutShort).collect(Collectors.toList());
    }

    @Override
    public List<ItemDtoOut> getItemByParams(String partnerId, ItemQueryParams params, Integer from, Integer size) {
        Partner partner = partnerService.findPartnerById(partnerId);
        List<Item> items = itemRepository.getItemByParams(params, from, size);
        return items.stream().map(x -> ItemMapper.toItemDtoOut(x, partner.getDiscount())).collect(Collectors.toList());
    }

    @Override
    public List<ItemDtoOut> getItemsPrices(String userId, String parentId, String partnerId, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        userService.findById(userId);
        Partner partner = partnerService.findPartnerById(partnerId);
        Page<Item> items;
        if(parentId == null) items = itemRepository.findItems(pageRequest);
        else items = itemRepository.findItemsByParentId(parentId, pageRequest);
        return items.stream().map(x -> ItemMapper.toItemDtoOut(x, partner.getDiscount())).collect(Collectors.toList());
    }
}
