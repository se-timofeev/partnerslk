package ru.planetnails.partnerslk.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.exception.BadRequestException;
import ru.planetnails.partnerslk.exception.LoadingError;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.group.Group;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.item.queryParams.GetItemsParams;
import ru.planetnails.partnerslk.model.item.queryParams.ItemQueryParams;
import ru.planetnails.partnerslk.model.item.dto.ItemAddDto;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoOut;
import ru.planetnails.partnerslk.model.item.dto.GroupDtoOut;
import ru.planetnails.partnerslk.model.item.dto.ItemMapper;
import ru.planetnails.partnerslk.model.item.queryParams.ItemSortBy;
import ru.planetnails.partnerslk.model.item.queryParams.SortType;
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
            if (itemAddDto.getLevel() == 3) items.add(ItemMapper.fromItemAddDtoToItem(itemAddDto));
            else if (itemAddDto.getLevel() == 2) groupsSecondLevel.add(ItemMapper.fromItemAddDtoToGroup(itemAddDto));
            else if (itemAddDto.getLevel() == 1) groupsFirstLevel.add(ItemMapper.fromItemAddDtoToGroup(itemAddDto));
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
    public Page<ItemDtoOut> getItems(GetItemsParams params) {
        Partner partner = partnerService.findPartnerById(params.getPartnerId());
        PageRequest pageRequest = getPageWithSort(params);
        Page<Item> items;
        if (params.getLevel() != null && params.getLevel() == 1) {
            List<String> groupIds = groupRepository.findIdListGroupId(params.getGroupId());
            items = itemRepository.findItemsByFirstLevelGroup(groupIds, pageRequest);
        } else if (params.getGroupId() == null) items = itemRepository.findAllNotOutOfStock(pageRequest);
        else items = itemRepository.findItemsBySecondLevelGroup(params.getGroupId(), pageRequest);
        return items.map(x -> ItemMapper.toItemDtoOut(x, partner.getDiscount()));
    }

    @Override
    public ItemDtoOut getItemById(String itemId, String partnerId) {
        Partner partner = partnerService.findPartnerById(partnerId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Item с id = %s не найден", itemId)));
        return ItemMapper.toItemDtoOut(item, partner.getDiscount());
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

    @Override
    public void deleteItems(List<String> itemsId) {
        if (!itemsId.isEmpty()) itemRepository.itemOutOfStock(itemsId);
    }

    private PageRequest getPageWithSort(GetItemsParams params) {
        if (params.getSortType() != null && params.getSortBy() != null) {
            String sortBy = ItemSortBy.getSortBy(params.getSortBy());
            Sort.Direction sortType = SortType.getSortType(params.getSortType());
            return PageRequest.of(params.getFrom() / params.getSize(), params.getSize(),
                    Sort.by(sortType, sortBy));
        } else if (params.getSortType() == null && params.getSortBy() != null)
            throw new BadRequestException("При запросе сортировки необходимо указать ее тип: 'asc' / 'desc'");
        else return PageRequest.of(params.getFrom() / params.getSize(), params.getSize());
    }
}
