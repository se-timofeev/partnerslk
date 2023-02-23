package ru.planetnails.partnerslk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderVt;
import ru.planetnails.partnerslk.model.order.dto.OderVtAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderMapper;
import ru.planetnails.partnerslk.model.order.dto.vtOrderStatusesAddDto;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;
import ru.planetnails.partnerslk.repository.*;
import ru.planetnails.partnerslk.repository.itemRepository.ItemRepository;
import ru.planetnails.partnerslk.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ContractorRepository contractorRepository;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(ContractorRepository contractorRepository, OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.contractorRepository = contractorRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String add(OrderAddDto orderAddDto) {
        log.info("Add new order");
        Contractor contractor = contractorRepository.getReferenceById(orderAddDto.getContractorId());
        List<OrderVt> orderVtList = new ArrayList<>();
        for (OderVtAddDto oderVtAddDto : orderAddDto.getOrderVts()) {
            OrderVt orderVt = OrderMapper.fromOrderVtAddDtoToOrderVt(oderVtAddDto,
                    itemRepository.getReferenceById(oderVtAddDto.getItemId()));
            orderVtList.add(orderVt);
        }
        List<vtOrderStatuses> vtOrderStatusesList = new ArrayList<>();
        for (vtOrderStatusesAddDto vtOrderStatusesAddDto : orderAddDto.getVtOrderStatuses()) {
            vtOrderStatuses vtOrderStatuses = OrderMapper.fromVtOrderStatusesAddDtoToVtOrderStatuses(
                    vtOrderStatusesAddDto, userRepository.getReferenceById(vtOrderStatusesAddDto.getUserId()));
            vtOrderStatusesList.add(vtOrderStatuses);
        }
        orderRepository.save(OrderMapper.fromOrderAddDtoOrder(orderAddDto, contractor, orderVtList, vtOrderStatusesList));
        Order newOrder = orderRepository.findOrderByContractor(contractor);
        UUID orderID = newOrder.getId();
        return orderID.toString();
    }
}

