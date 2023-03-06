package ru.planetnails.partnerslk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderGenerator;
import ru.planetnails.partnerslk.model.order.OrderVt;
import ru.planetnails.partnerslk.model.order.dto.*;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.repository.ContractorRepository;
import ru.planetnails.partnerslk.repository.OrderRepository;
import ru.planetnails.partnerslk.repository.PartnerRepository;
import ru.planetnails.partnerslk.repository.UserRepository;
import ru.planetnails.partnerslk.repository.itemRepository.ItemRepository;
import ru.planetnails.partnerslk.service.OrderService;

import javax.validation.ValidationException;
import java.util.*;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Map<String, OrderGenerator> orderGenerators;

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
        Contractor contractor = contractorRepository.findById(orderAddDto.getContractorId())
                .orElseThrow(() -> new NotFoundException("Contractor not found"));
        log.info("Contractor with id {} found", orderAddDto.getContractorId());
        Partner partner = partnerRepository.findById(orderAddDto.getPartnerId())
                .orElseThrow(() -> new NotFoundException("Partner not found"));
        log.info("Partner with id {} found", orderAddDto.getPartnerId());
        List<OrderVt> orderVtList = new ArrayList<>();
        if (orderAddDto.getOrderVts() == null) {
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
            User user = userRepository.findById(vtOrderStatusesAddDto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            log.info("User with id {} found", vtOrderStatusesAddDto.getUserId());
            vtOrderStatuses vtOrderStatuses = OrderMapper.fromVtOrderStatusesAddDtoToVtOrderStatuses(
                    vtOrderStatusesAddDto, user);
            vtOrderStatusesList.add(vtOrderStatuses);
        }

        return orderRepository.save(OrderMapper.fromOrderAddDtoOrder(orderAddDto, contractor, partner, orderVtList,
                vtOrderStatusesList)).getId();
    }

    @Override
    public Order findById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        log.info("Order with id {} found", orderId);
        return order;
    }

    @Override
    public List<Order> findAllByPartnerId(String partnerId, PageRequest pageRequest) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new NotFoundException("Partner not found"));
        log.info("Partner with id {} found", partnerId);
        return orderRepository.findAllByPartner(partner, pageRequest);
    }

    @Override
    public OrderOutDto setStatusForOrder(String orderId, String status, String userId) {
        OrderGenerator orderGenerator = orderGenerators.get(status);
        if (!orderGenerators.containsKey(status)) {
            log.error("Unknown state: UNSUPPORTED_STATUS: {}", status);
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        return orderGenerator.setStatusForOrder(orderId, userId);
    }
}
