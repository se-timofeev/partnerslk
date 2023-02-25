package ru.planetnails.partnerslk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderVt;
import ru.planetnails.partnerslk.model.order.dto.OderVtAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderAddDto;
import ru.planetnails.partnerslk.model.order.dto.OrderMapper;
import ru.planetnails.partnerslk.model.order.dto.vtOrderStatusesAddDto;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.repository.ContractorRepository;
import ru.planetnails.partnerslk.repository.OrderRepository;
import ru.planetnails.partnerslk.repository.PartnerRepository;
import ru.planetnails.partnerslk.repository.UserRepository;
import ru.planetnails.partnerslk.repository.itemRepository.ItemRepository;
import ru.planetnails.partnerslk.service.OrderService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ContractorRepository contractorRepository;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final PartnerRepository partnerRepository;

    @Autowired
    public OrderServiceImpl(ContractorRepository contractorRepository, OrderRepository orderRepository,
                            ItemRepository itemRepository, UserRepository userRepository, PartnerRepository partnerRepository) {
        this.contractorRepository = contractorRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
    }

    @Override
    @Transactional
    public String add(OrderAddDto orderAddDto) {
        log.info("Add new order");
        Contractor contractor = contractorRepository.getReferenceById(orderAddDto.getContractorId());
        Partner partner = partnerRepository.getReferenceById(orderAddDto.getPartnerId());
        List<OrderVt> orderVtList = new ArrayList<>();
        if (orderAddDto.getOrderVts() == null){
            orderVtList = Collections.emptyList();
        } else {
            for (OderVtAddDto oderVtAddDto : orderAddDto.getOrderVts()) {
                OrderVt orderVt = OrderMapper.fromOrderVtAddDtoToOrderVt(oderVtAddDto,
                        itemRepository.getReferenceById(oderVtAddDto.getItemId()));
                orderVtList.add(orderVt);
            }
        }
        List<vtOrderStatuses> vtOrderStatusesList = new ArrayList<>();
        for (vtOrderStatusesAddDto vtOrderStatusesAddDto : orderAddDto.getVtOrderStatuses()) {
            vtOrderStatuses vtOrderStatuses = OrderMapper.fromVtOrderStatusesAddDtoToVtOrderStatuses(
                    vtOrderStatusesAddDto, userRepository.getReferenceById(vtOrderStatusesAddDto.getUserId()));
            vtOrderStatusesList.add(vtOrderStatuses);
        }

        return orderRepository.save(OrderMapper.fromOrderAddDtoOrder(orderAddDto, contractor, partner, orderVtList,
                vtOrderStatusesList)).getId();
    }

    @Override
    public Order findById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        log.info("user with name {} found", orderId);
        return order;
    }
}

