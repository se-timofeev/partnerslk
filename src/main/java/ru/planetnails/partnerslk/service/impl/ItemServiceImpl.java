package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.exception.LoadingError;
import ru.planetnails.partnerslk.model.group.Group;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.item.ItemQueryParams;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.GroupDtoOut;
import ru.planetnails.partnerslk.model.item.dto.ItemMapper;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.repository.groupRepository.GroupRepository;
import ru.planetnails.partnerslk.repository.itemRepository.ItemRepository;
import ru.planetnails.partnerslk.service.ItemService;
import ru.planetnails.partnerslk.service.PartnerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor

public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

    private GroupRepository groupRepository;
    private PartnerService partnerService;

    @Override
    @Transactional
    @Async
    public void add(List<ItemAddDto> itemsAddDto) {
        log.info("Add items as List ");
        List<Item> items = new ArrayList<>();
        List<Group> groupsFirstLevel = new ArrayList<>();
        List<Group> groupsSecondLevel = new ArrayList<>();
        for (ItemAddDto itemAddDto : itemsAddDto) {
            if (itemAddDto.getLevel() == 3) {
                items.add(ItemMapper.fromItemAddDtoToItem(itemAddDto));
            } else if (itemAddDto.getLevel() == 2) {
                groupsSecondLevel.add(ItemMapper.fromItemAddDtoToGroup(itemAddDto));
            } else if (itemAddDto.getLevel() == 1) {
                groupsFirstLevel.add(ItemMapper.fromItemAddDtoToGroup(itemAddDto));
            }
        }
        try {
            groupRepository.saveAll(groupsFirstLevel);
            groupRepository.saveAll(groupsSecondLevel);
            itemRepository.saveAll(items);
        } catch (Exception e) {
            throw new LoadingError("Ошибка загрузки данных");
        }
    }

    @Override
    public Page<ItemDtoOut> getItems(String groupId, Integer from, Integer size, String partnerId, Integer level) {
        Partner partner = partnerService.findPartnerById(partnerId);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Page<Item> items;
        if (level != null && level == 1) {
            List<String> groupIds = groupRepository.findIdListGroupId(groupId);
            items = itemRepository.findItemsFirstLevelGroup(groupIds, pageRequest);
        } else if (groupId == null) items = itemRepository.findAll(pageRequest);
        else items = itemRepository.findItemsByGroupId(groupId, pageRequest);
        return items.map(x -> ItemMapper.toItemDtoOut(x, partner.getDiscount()));
    }

    @Override
    public List<GroupDtoOut> getFilteredGroups(Integer level, String groupId, Integer from, Integer size) {
        List<Group> groups = groupRepository.getFilteredGroups(level, groupId, from, size);
        return groups.stream().map(ItemMapper::toGroupDtoOut).collect(Collectors.toList());
    }

    @Override
    public List<ItemDtoOut> getItemByParams(String partnerId, ItemQueryParams params, Integer from, Integer size) {
        Partner partner = partnerService.findPartnerById(partnerId);
        List<Item> items = itemRepository.getItemByParams(params, from, size);
        return items.stream().map(x -> ItemMapper.toItemDtoOut(x, partner.getDiscount())).collect(Collectors.toList());
    }
}
