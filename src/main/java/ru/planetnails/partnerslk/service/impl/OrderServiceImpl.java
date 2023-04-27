package ru.planetnails.partnerslk.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.planetnails.partnerslk.broker.RabbitMQProducerService;
import ru.planetnails.partnerslk.exception.NotFoundException;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.order.*;
import ru.planetnails.partnerslk.model.order.dto.*;
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
    private Map<String, OrderGenerator> orderGeneratorMap;

    private final ContractorRepository contractorRepository;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final PartnerRepository partnerRepository;

    private final RabbitMQProducerService rabbitMQProducerService;

    @Autowired
    public OrderServiceImpl(ContractorRepository contractorRepository, OrderRepository orderRepository,
                            ItemRepository itemRepository, UserRepository userRepository,
                            PartnerRepository partnerRepository, RabbitMQProducerService rabbitMQProducerService) {
        this.contractorRepository = contractorRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.rabbitMQProducerService = rabbitMQProducerService;
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
        List<VtOrderStatuses> vtOrderStatusesList = new ArrayList<>();
        User user = userRepository.findById(orderAddDto.getVtOrderStatuses().get(0).getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        log.info("User with id {} found", orderAddDto.getVtOrderStatuses().get(0).getUserId());
        VtOrderStatuses vtOrderStatuses = OrderMapper.addVtOrderStatuses(user.getId());
        vtOrderStatusesList.add(vtOrderStatuses);


        Order order = OrderMapper.fromOrderAddDtoOrder(orderAddDto, contractor, partner,
                convertToOrderVtList(orderAddDto), vtOrderStatusesList);

        // сохранение заказа после которого будет ясен id
        orderRepository.save(order);

        Gson gson = OrderMapper.getGson();
        String toJson = gson.toJson(OrderMapper.fromOrderToOrderOutDto(order));
        // сохраняем данные в брокере сообщений
        // обратить внимание, что ID крайне важен, так без него мастер система
        // не поймет как матчить данные
        rabbitMQProducerService.sendMessage(toJson);
        return order.getId();
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
    @Transactional
    public OrderOutDto statusForOrderUser(String orderId, String status, String user) {
        OrderGenerator orderGenerator = this.orderGeneratorMap.get(status);
        if (!this.orderGeneratorMap.containsKey(status)) {
            log.error("Unknown state: UNSUPPORTED_STATUS: {}", status);
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        return orderGenerator.setStatusForOrderUser(orderId, user);
    }

    @Override
    @Transactional
    public Order statusForOrderManager(String orderId, String status, String user) {
        OrderGenerator orderGenerator = this.orderGeneratorMap.get(status);
        if (!orderGeneratorMap.containsKey(status)) {
            log.error("Unknown state: UNSUPPORTED_STATUS: {}", status);
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        return orderGenerator.setStatusForOrderManager(orderId, user);
    }

    @Override
    @Transactional
    public String update(OrderAddDto orderAddDto, String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        List<VtOrderStatuses> vtOrderStatusesList = order.getVtOrderStatuses();
        User user = userRepository.findById(orderAddDto.getVtOrderStatuses().get(0).getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        log.info("User with id {} found", orderAddDto.getVtOrderStatuses().get(0).getUserId());
        VtOrderStatuses vtOrderStatuses = OrderMapper.updateVtOrderStatuses(user.getId());
        vtOrderStatusesList.add(vtOrderStatuses);

        order.setOrderVts(convertToOrderVtList(orderAddDto));
        order.setVtOrderStatuses(vtOrderStatusesList);
        order.setSumWithDiscount(orderAddDto.getSumWithDiscount());
        order.setSumOfDiscount(orderAddDto.getSumOfDiscount());
        order.setSumWithoutDiscount(orderAddDto.getSumWithoutDiscount());
        order.setStatus(OrderStatus.UPDATED);

        return orderRepository.save(order).getId();
    }

    @Override
    @Transactional
    public void rabbitUpdate(String message) throws JsonProcessingException {
        OrderRabbitAddDto orderRabbitAddDto = OrderMapper.fromMessageToOrderRabbitAddDto(message);
        Order order = statusForOrderManager(orderRabbitAddDto.getOrderId(), orderRabbitAddDto.getStatus(),
                orderRabbitAddDto.getUser());

        if (orderRabbitAddDto.getOrderVts() != null) {
            order.setOrderVts(rabbitConvertToOrderVtList(orderRabbitAddDto));
        }
        if (orderRabbitAddDto.getSumWithDiscount() != null) {
            order.setSumWithDiscount(orderRabbitAddDto.getSumWithDiscount());
        }
        if (orderRabbitAddDto.getSumOfDiscount() != null) {
            order.setSumOfDiscount(orderRabbitAddDto.getSumOfDiscount());
        }
        if (orderRabbitAddDto.getSumWithoutDiscount() != null) {
            order.setSumWithoutDiscount(orderRabbitAddDto.getSumWithoutDiscount());
        }

        log.info("Order was updated successfully with orderId = {}", order.getId());
    }

    private List<OrderVt> rabbitConvertToOrderVtList(OrderRabbitAddDto orderRabbitAddDto) {
        List<OrderVt> orderVtList = new ArrayList<>();
        if (orderRabbitAddDto.getOrderVts() == null) {
            orderVtList = Collections.emptyList();
        } else {
            for (OderVtAddDto oderVtAddDto : orderRabbitAddDto.getOrderVts()) {
                OrderVt orderVt = OrderMapper.fromRabbitOrderVtAddDtoToOrderVt(oderVtAddDto,
                        itemRepository.getReferenceById(oderVtAddDto.getItemId()));
                orderVtList.add(orderVt);
            }
        }
        return orderVtList;
    }

    private List<OrderVt> convertToOrderVtList(OrderAddDto orderAddDto) {
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
        return orderVtList;
    }
}
